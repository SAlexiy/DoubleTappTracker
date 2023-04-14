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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.consts.keys.HabitCreatorArgumentsKeys
import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.datamodel.Habit
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

    private lateinit var habit: Habit
    private lateinit var typeValue: TypeValue


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = HabitCreatorViewModel.getViewModel(context!!)

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



                    //TODO перенести label в ресурсы
                    val name = viewModel.name.value.collectAsState()
                    TextField(
                        label = "name",
                        textState = name.value,
                        onValueChange = {viewModel.name.setValue(it)}
                    )

                    val description = viewModel.description.value.collectAsState()
                    TextField(
                        label = "description",
                        textState = description.value,
                        onValueChange = {viewModel.description.setValue(it)}
                    )

                    val periodicity = viewModel.periodicity.value.collectAsState()
                    TextField(
                        label = "periodicity",
                        textState = periodicity.value,
                        onValueChange = {viewModel.periodicity.setValue(it)}
                    )

                    val priority = viewModel.priority.value.collectAsState()
                    val spinnerExpanded = viewModel.isSpinnerExpanded.value.collectAsState()
                    ExposedDropdownMenuBoxTypeHabit(
                        isExpanded = spinnerExpanded.value,
                        changeSpinnerExpanded = { viewModel.isSpinnerExpanded.setValue(it) },
                        selectedPriority = priority.value,
                        onPriorityChange = { viewModel.priority.setValue(it) }
                    )

                    val selectedType = viewModel.type.value.collectAsState()
                    RadioButtons(
                        selectedType = selectedType.value,
                        onTypeChange = {viewModel.type.setValue(it)}
                    )

                    val color = viewModel.color.value.collectAsState()
                    SelectedColor(color = color.value)

                    ColorPicker(onColorChange = {viewModel.color.setValue(it)})

                    //кнопки "удалить" и "сохранить"
                    val isNewHabit = arguments?.getBoolean(HabitCreatorArgumentsKeys.newHabit) ?: false
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp),
                        horizontalArrangement =  Arrangement.SpaceAround){
                        if(!isNewHabit){

                            StandardButton(
                                text = resources.getString(R.string.delete),
                                buttonColor = Color.Red
                            ) {

                                viewModel.deleteHabit()

                                findNavController().navigate(
                                    R.id.action_habitCreatorFragment_to_habitListFragment
                                )
                            }
                        }

                        StandardButton(
                            text = resources.getString(R.string.save),
                        ) {
                            viewModel.updateHabitParams()

                            if(isNewHabit){
                                viewModel.insertHabit()
                            } else {
                                viewModel.updateHabit()
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

        arguments?.let {
            habit = it.getSerializable(HabitCreatorArgumentsKeys.habit) as Habit
            typeValue = viewModel.getValueType(habit.type)
        }

        viewModel.setParams(
            habit = habit,
            typeValue = typeValue
        )
    }


    companion object{
        fun newBundle(habit: Habit? = null) : Bundle {
            var argHabit = habit
            val bundle = Bundle()

            if (argHabit == null){

                argHabit = Habit(
                    habitId = UUID.randomUUID().toString(),
                )

                bundle.putBoolean(HabitCreatorArgumentsKeys.newHabit, true)
            } else {
                bundle.putBoolean(HabitCreatorArgumentsKeys.newHabit, false)
            }

            bundle.putSerializable(HabitCreatorArgumentsKeys.habit, argHabit)

            return bundle
        }


    }
}
