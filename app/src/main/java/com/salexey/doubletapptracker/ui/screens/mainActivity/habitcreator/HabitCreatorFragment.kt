package com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
import androidx.navigation.findNavController
import com.salexey.datamodels.habit.Habit
import com.salexey.doubletapptracker.MainApplication
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.consts.keys.HabitCreatorArgumentsKeys
import com.salexey.doubletapptracker.ui.elements.*
import com.salexey.doubletapptracker.ui.elements.buttons.StandardButton
import com.salexey.doubletapptracker.ui.elements.list.colorlist.ColorPicker
import com.salexey.doubletapptracker.ui.elements.list.colorlist.SelectedColor
import com.salexey.habit_manager.HabitRepository
import javax.inject.Inject


/**
 * фрагмент для создания новых привычек
 *
 * записывает Habit в таблицу habit
 */
class HabitCreatorFragment : Fragment() {

    private lateinit var viewModel: HabitCreatorViewModel

    private lateinit var habit: Habit

    @Inject
    lateinit var habitRepository: HabitRepository

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        (requireActivity().application as MainApplication).applicationComponent.inject(this)
        viewModel = HabitCreatorViewModel.getViewModel(habitRepository)

    }

    @RequiresApi(Build.VERSION_CODES.O)
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


                    val name = viewModel.name.value.collectAsState()
                    TextField(
                        label = "Name",
                        textState = name.value,
                        onValueChange = {viewModel.name.setValue(it)}
                    )

                    val description = viewModel.description.value.collectAsState()
                    TextField(
                        label = "Description",
                        textState = description.value,
                        onValueChange = {viewModel.description.setValue(it)}
                    )

                    val frequency = viewModel.frequency.value.collectAsState()
                    val frequencySinnerExpanded = viewModel.isFrequencySpinnerExpanded.value.collectAsState()
                    ExposedDropdownMenuBoxTypeHabit(
                        text = "Frequency",
                        isExpanded = frequencySinnerExpanded.value,
                        changeSpinnerExpanded = { viewModel.isFrequencySpinnerExpanded.setValue(it) },
                        selectedPriority = frequency.value,
                        onPriorityChange = { viewModel.frequency.setValue(it) },
                        valueList = listOf(1,2,3,4,5,6,7)
                    )

                    val priority = viewModel.priority.value.collectAsState()
                    val prioritySpinnerExpanded = viewModel.isPrioritySpinnerExpanded.value.collectAsState()
                    ExposedDropdownMenuBoxTypeHabit(
                        text = "Priority",
                        isExpanded = prioritySpinnerExpanded.value,
                        changeSpinnerExpanded = { viewModel.isPrioritySpinnerExpanded.setValue(it) },
                        selectedPriority = priority.value,
                        onPriorityChange = { viewModel.priority.setValue(it) },
                        valueList = listOf(0,1,2)
                    )

                    val selectedType = viewModel.type.value.collectAsState()
                    RadioButtons(
                        text = "Type",
                        selectedType = selectedType.value,
                        onTypeChange = {viewModel.type.setValue(it)},
                        type = listOf(0,1)
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
        }

        viewModel.setParams(
            habit = habit
        )
    }


    companion object{
        fun newBundle(habit: Habit? = null) : Bundle {
            var argHabit = habit
            val bundle = Bundle()

            if (argHabit == null){

                argHabit = Habit(
                    uid = "",
                    color = 0,
                    count = 0,
                    date = 0,
                    description = "",
                    doneDates = mutableListOf(),
                    frequency = 0,
                    priority = 0,
                    title = "",
                    type = 0
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
