package com.salexey.doubletapptracker.ui.elements.list.habitlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorFragment
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.HabitListViewModel

@Composable
fun habitList(viewModel: HabitListViewModel, onClick: ()-> Unit) {

    val habitList = viewModel.habitList.collectAsState()

    LazyColumn(modifier = Modifier
        .fillMaxSize()) {

        items(habitList.value.size) {currentHabit->

            habitUnit(habit = habitList.value[currentHabit],
                onClick = {

                    viewModel.bundle = HabitCreatorFragment
                        .newInstanceHabitCreatorFragment(habitList.value[currentHabit]).arguments

                    onClick()

                }
            )

        }

    }
}
