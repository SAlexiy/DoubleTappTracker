package com.salexey.doubletapptracker.features.filter

import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.datamodel.Habit

object HabitFilter {

    /**
     * фильтрует список habit по параметру priority
     */
    fun byPriority(habits: List<Habit>, priority: Int): List<Habit> {

        return habits.filter { it.priority == priority }
    }

    /**
     * фильтрует список habit по параметру color
     */
    fun byColor(habits: List<Habit>, color: Int): List<Habit> {

        return habits.filter { it.color == color }
    }
}
