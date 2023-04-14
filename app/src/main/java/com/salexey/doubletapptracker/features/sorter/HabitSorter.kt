package com.salexey.doubletapptracker.features.sorter

import com.salexey.doubletapptracker.consts.values.TypeSort
import com.salexey.doubletapptracker.datamodel.Habit

object HabitSorter {

    fun byType(habits: List<Habit>, type: TypeSort): List<Habit> {
        return when(type){
            TypeSort.NAME -> byName(habits)
            TypeSort.PRIORITY -> byPriority(habits)
            else -> habits
        }
    }

    /**
     * сортирует список habit по параметру priority
     */
    fun byPriority(habits: List<Habit>): List<Habit> {

        return habits.sortedBy { it.priority }
    }

    /**
     * сортирует список habit по параметру name
     */
    fun byName(habits: List<Habit>): List<Habit> {

        return habits.sortedBy { it.name }
    }
}

