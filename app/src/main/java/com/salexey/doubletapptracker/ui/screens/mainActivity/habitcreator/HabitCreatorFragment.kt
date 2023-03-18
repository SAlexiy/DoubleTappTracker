package com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.consts.HabitCreatorArgumentsKeys
import com.salexey.doubletapptracker.datamodel.Habit
import com.salexey.doubletapptracker.room.AppDB
import com.salexey.doubletapptracker.room.HabitRepository
import com.salexey.doubletapptracker.ui.elements.*
import com.salexey.doubletapptracker.ui.elements.buttons.StandardButton
import com.salexey.doubletapptracker.ui.elements.list.colorlist.ColorPicker
import com.salexey.doubletapptracker.ui.elements.list.colorlist.SelectedColor
import kotlinx.coroutines.launch
import java.util.UUID


/**
 * фрагмент для создания новых привычек
 *
 * записывает Habit в таблицу habit
 */
class HabitCreatorFragment : Fragment() {

    private lateinit var viewModel: HabitCreatorViewModel
    private lateinit var db: AppDB
    private lateinit var habitRepository: HabitRepository



    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[HabitCreatorViewModel::class.java]

        db = AppDB.getInstance(this@HabitCreatorFragment.context!!)
        habitRepository = HabitRepository(db.habitDao())
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_creator, container, false).apply {
            findViewById<ComposeView>(R.id.habit_creator_compose_view).setContent {

                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current
                val interactionSource = remember { MutableInteractionSource() }

                Column(modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    val name = viewModel.name.collectAsState()
                    TextField(
                        label = "name",
                        textState = name.value,
                        onValueChange = {viewModel.setName(it)}
                    )

                    val description = viewModel.description.collectAsState()
                    TextField(
                        label = "description",
                        textState = description.value,
                        onValueChange = {viewModel.setDescription(it)}
                    )

                    val periodicity = viewModel.periodicity.collectAsState()
                    TextField(
                        label = "periodicity",
                        textState = periodicity.value,
                        onValueChange = {viewModel.setPeriodicity(it)}
                    )

                    val priority = viewModel.priority.collectAsState()
                    val spinnerExpanded = viewModel.spinnerExpanded.collectAsState()
                    ExposedDropdownMenuBoxTypeHabit(
                        isExpanded = spinnerExpanded.value,
                        changeSpinnerExpanded = { viewModel.changeSpinnerExpanded(it) },
                        selectedPriority = priority.value,
                        onPriorityChange = { viewModel.setPriority(it) }
                    )

                    val selectedType = viewModel.type.collectAsState()
                    RadioButtons(
                        selectedType = selectedType.value,
                        onTypeChange = {viewModel.setType(it)}
                    )

                    val color = viewModel.color.collectAsState()
                    SelectedColor(color = color.value)

                    ColorPicker(onColorChange = {viewModel.setColor(it)})

                    //кнопки "удалить" и "сохранить"
                    val bool = arguments?.getBoolean(HabitCreatorArgumentsKeys.newHabit) ?: false
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp),
                        horizontalArrangement =  Arrangement.SpaceAround){
                        if(!bool){

                            StandardButton(
                                text = resources.getString(R.string.delete),
                                buttonColor = Color.Red
                            ) {
                                viewModel.updateHabit()

                                viewLifecycleOwner.lifecycleScope.launch {
                                    habitRepository.deleteHabit(viewModel.habit.value)
                                }

                                findNavController().navigate(
                                    R.id.action_habitCreatorFragment_to_habitListFragment
                                )
                            }
                        }

                        StandardButton(
                            text = resources.getString(R.string.save),
                        ) {
                            viewModel.updateHabit()

                            if(bool){
                                viewLifecycleOwner.lifecycleScope.launch {
                                    habitRepository.insertHabit(viewModel.habit.value)
                                }
                            } else {
                                viewLifecycleOwner.lifecycleScope.launch {
                                    habitRepository.updateHabit(viewModel.habit.value)
                                }
                            }

                            findNavController().navigate(
                                R.id.action_habitCreatorFragment_to_habitListFragment
                            )
                        }
                    }



                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habit = arguments?.getSerializable(HabitCreatorArgumentsKeys.habit) as Habit

        viewModel.setParams(habit)
    }


    companion object{

        fun newBundleHabitCreatorFragment(habit: Habit) : Bundle{
            val bundle = Bundle()

            bundle.putSerializable(HabitCreatorArgumentsKeys.habit, habit)
            bundle.putBoolean(HabitCreatorArgumentsKeys.newHabit, false)

            return bundle
        }

        fun newBundleHabitCreatorFragment() : Bundle{
            val bundle = Bundle()

            bundle.putSerializable(HabitCreatorArgumentsKeys.habit,
                Habit(
                    habitId = UUID.randomUUID().toString(),
                ))
            bundle.putBoolean(HabitCreatorArgumentsKeys.newHabit, true)

            return bundle
        }
    }
}
