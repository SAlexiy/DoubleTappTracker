package com.salexey.doubletapptracker.ui.elements.list.habitlist

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.salexey.doubletapptracker.datamodel.Habit
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorFragment

@Composable
fun HabitList(habitList: List<Habit>, onClick: (Bundle)-> Unit) {

    LazyColumn(modifier = Modifier
        .fillMaxSize()) {

        items(habitList.size) {currentHabit->

            habitUnit(habit = habitList[currentHabit],
                onClick = {

                    onClick(
                        HabitCreatorFragment.newBundle(habitList[currentHabit])
                    )

                }
            )

        }

    }
}
