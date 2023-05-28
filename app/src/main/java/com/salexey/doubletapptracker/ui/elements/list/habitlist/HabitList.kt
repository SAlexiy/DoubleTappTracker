package com.salexey.doubletapptracker.ui.elements.list.habitlist

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.salexey.datamodels.habit.Habit
import com.salexey.doubletapptracker.features.filter.HabitFilter
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorFragment

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitList(
    habitList: List<Habit>,
    onClick: (Bundle) -> Unit,
    buttonClick: (Habit) -> Unit,
    filterTypeValue: Int,
    filterPriority: Int,
    filterColor: Int
) {
    var list = mutableListOf<Habit>()

    if (filterTypeValue != -1)
        list = HabitFilter.byType(habitList.toMutableList(), filterTypeValue)

    if (filterPriority != -1)
        list = HabitFilter.byPriority(list, filterPriority)

    if (filterColor != -1)
        list = HabitFilter.byColor(list, filterColor)

    LazyColumn(modifier = Modifier
        .fillMaxSize()) {

        items(list.size) {currentHabit->

            habitUnit(habit = list[currentHabit],
                onClick = {

                    onClick(
                        HabitCreatorFragment.newBundle(list[currentHabit])
                    )

                },
                buttonClick = { buttonClick(list[currentHabit]) }

            )

        }

    }
}
