package com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
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
import com.salexey.doubletapptracker.datamodel.Habit
import com.salexey.doubletapptracker.room.AppDB
import com.salexey.doubletapptracker.room.HabitRepository
import com.salexey.doubletapptracker.ui.elements.*
import com.salexey.doubletapptracker.ui.elements.buttons.standartButton
import com.salexey.doubletapptracker.ui.elements.list.colorlist.colorPicker
import com.salexey.doubletapptracker.ui.elements.list.colorlist.selectedColor
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
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = "id: ${viewModel.habit.collectAsState().value.habitId}",
                        modifier = Modifier.padding(10.dp))

                    nameField(viewModel = viewModel)

                    descriptionField(viewModel = viewModel)

                    periodicityField(viewModel = viewModel)

                    exposedDropdownMenuBoxTypeHabit(viewModel)

                    radioButtons(viewModel)

                    selectedColor(viewModel)

                    colorPicker(viewModel)

                    //кнопки "удалить" и "сохранить"
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp),
                        horizontalArrangement =  Arrangement.SpaceAround){
                        if(!arguments?.getBoolean("newHabit")!!){
                            standartButton(text = "Удалить", buttonColor = Color.Red) {
                                viewModel.updateHabit()

                                viewLifecycleOwner.lifecycleScope.launch {
                                    habitRepository.deleteHabit(viewModel.habit.value)
                                }

                                findNavController().navigate(
                                    R.id.action_habitCreatorFragment_to_habitListFragment
                                )
                            }
                        }



                        standartButton(text = "Сохранить") {
                            viewModel.updateHabit()

                            if(arguments?.getBoolean("newHabit")!!){
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

        viewModel.setHabit(arguments?.getSerializable("habit") as Habit)
        viewModel.setName(viewModel.habit.value.name)
        viewModel.setDescription(viewModel.habit.value.description)
        viewModel.setPeriodicity(viewModel.habit.value.periodicity)
        viewModel.setPriority(viewModel.habit.value.priority)
        viewModel.setColor(viewModel.habit.value.color)
    }


    companion object{

        fun newInstanceHabitCreatorFragment(habit: Habit) : HabitCreatorFragment{
            val fragment = HabitCreatorFragment()
            val bundle = Bundle()


            bundle.putSerializable("habit", habit)
            bundle.putBoolean("newHabit", false)


            fragment.arguments = bundle

            return fragment
        }

        fun newInstanceHabitCreatorFragment() : HabitCreatorFragment{
            val fragment = HabitCreatorFragment()
            val bundle = Bundle()


            bundle.putSerializable("habit",
                Habit(
                    habitId = UUID.randomUUID().toString(),
                ))
            bundle.putBoolean("newHabit", true)


            fragment.arguments = bundle

            return fragment
        }
    }
}
