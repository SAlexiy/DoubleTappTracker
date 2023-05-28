package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.page

import com.salexey.doubletapptracker.MainApplication
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.findNavController
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.consts.keys.HabitListArgumentsKeys
import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.ui.elements.BottomSheet
import com.salexey.doubletapptracker.ui.elements.ExposedDropdownMenuBoxTypeHabit
import com.salexey.doubletapptracker.ui.elements.buttons.StandardButton
import com.salexey.doubletapptracker.ui.elements.list.colorlist.ColorPicker
import com.salexey.doubletapptracker.ui.elements.list.colorlist.SelectedColor
import com.salexey.doubletapptracker.ui.elements.list.habitlist.HabitList
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.HabitListViewModel
import com.salexey.habit_manager.HabitRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * фрагмент для отображения привычек
 *
 * читает Habit из таблицы habit
 */
class HabitListPageFragment : Fragment() {
    private var type: TypeValue? = null

    private lateinit var viewModel: HabitListViewModel


    @Inject
    lateinit var habitRepository: HabitRepository


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as MainApplication).applicationComponent.inject(this)

        viewModel = HabitListViewModel.getViewModel(habitRepository)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_habit_list_page, container, false).apply {
            findViewById<ComposeView>(R.id.habit_list_compose_view).setContent {



                //bottom sheet
                val state = rememberBottomSheetScaffoldState(bottomSheetState =
                    BottomSheetState(BottomSheetValue.Collapsed)
                )

                val coroutineScope = rememberCoroutineScope()

                BottomSheet(state,
                    sheetContent = {

                        Column(modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .background(color = Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally){

                            val priorityFilter = viewModel.filterPriority.value.collectAsState()
                            val prioritySpinnerExpanded = viewModel.isPrioritySpinnerExpanded.value.collectAsState()
                            ExposedDropdownMenuBoxTypeHabit(
                                text = "Priority",
                                isExpanded = prioritySpinnerExpanded.value,
                                changeSpinnerExpanded = { viewModel.isPrioritySpinnerExpanded.setValue(it) },
                                selectedPriority = priorityFilter.value,
                                onPriorityChange = { viewModel.filterPriority.setValue(it) },
                                valueList = listOf(0,1,2)
                            )

                            val colorFilter = viewModel.filterColor.value.collectAsState()
                            SelectedColor(color = colorFilter.value)

                            ColorPicker(onColorChange = {viewModel.filterColor.setValue(it)})


                            StandardButton(modifier = Modifier.padding(20.dp),
                                text = "Сбросить фильтр", width = 300, height = 72) {
                                viewModel.filterColor.setValue(-1)
                                viewModel.filterPriority.setValue(-1)
                            }

                        }

                    },
                    screenContent = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val habitList = viewModel.habitList.value.collectAsState()
                            val filterTypeValue = viewModel.filterTypeValue.value.collectAsState()
                            val filterColor = viewModel.filterColor.value.collectAsState()
                            val filterPriority = viewModel.filterPriority.value.collectAsState()

                            Row(){
                                StandardButton(modifier = Modifier.padding(10.dp).fillMaxWidth(),
                                    text = "Фильтр", width = 300){

                                    coroutineScope.launch {

                                        if (state.bottomSheetState.isCollapsed){
                                            viewModel.isFabView.setValue(false)
                                            state.bottomSheetState.expand()
                                        }else{
                                            viewModel.isFabView.setValue(true)
                                            state.bottomSheetState.collapse()
                                        }
                                    }
                                }
                            }

                            HabitList(
                                filterTypeValue = filterTypeValue.value,
                                filterColor = filterColor.value,
                                filterPriority = filterPriority.value,
                                habitList = habitList.value,
                                onClick = {
                                    findNavController().navigate(
                                        R.id.action_habitListFragment_to_habitCreatorFragment,
                                        it
                                    )
                                },

                                buttonClick = {
                                    viewModel.updateHabitDone(it)
                                }
                            )

                        }
                    }
                )

            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            type = it.getString(HabitListArgumentsKeys.TYPE).let {str ->

                if (str != null) {
                    TypeValue.valueOf(str)
                } else{
                    null
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
    }



    companion object{
        fun newInstance(type: TypeValue?): HabitListPageFragment{
            val fragment = HabitListPageFragment()
            val args = Bundle()

            type?.let {

                args.putString(HabitListArgumentsKeys.TYPE, it.name)

            }
            fragment.arguments = args

            return fragment
        }
    }
}