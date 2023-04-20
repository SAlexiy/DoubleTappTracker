package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.page

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.findNavController
import com.salexey.doubletapptracker.R
import com.salexey.doubletapptracker.consts.keys.HabitListArgumentsKeys
import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.ui.elements.BottomSheet
import com.salexey.doubletapptracker.ui.elements.buttons.StandardButton
import com.salexey.doubletapptracker.ui.elements.list.habitlist.HabitList
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.HabitListViewModel
import kotlinx.coroutines.launch
import java.util.*

/**
 * фрагмент для отображения привычек
 *
 * читает Habit из таблицы habit
 */
class HabitListPageFragment : Fragment() {
    private var type: TypeValue? = null

    private lateinit var viewModel: HabitListViewModel

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = HabitListViewModel.getViewModel(context!!)

    }


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

                        Box(modifier = Modifier
                            .height(400.dp)
                            .fillMaxWidth()
                            .background(color = Color.Blue)){

                        }

                    },
                    screenContent = {
                        Column {
                            val habitList = viewModel.habitList.value.collectAsState()
                            Row(){
                                StandardButton(){

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
                                habitList = viewModel.setFilter(habitList.value),
                                onClick = {
                                    findNavController().navigate(
                                        R.id.action_habitListFragment_to_habitCreatorFragment,
                                        it
                                    )
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