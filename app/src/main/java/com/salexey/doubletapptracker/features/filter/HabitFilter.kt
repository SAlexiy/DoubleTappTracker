package com.salexey.doubletapptracker.features.filter

import com.salexey.datamodels.habit.Habit


object HabitFilter {
    /**
     * фильтрует список habit по параметру priority
     */
    fun byType(habits: MutableList<Habit>, type: Int): MutableList<Habit> {

        return (habits.filter { it.type == type }).toMutableList()
    }

    /**
     * фильтрует список habit по параметру priority
     */
    fun byPriority(habits: MutableList<Habit>, priority: Int): MutableList<Habit> {

        return (habits.filter { it.priority == priority }).toMutableList()
    }

    /**
     * фильтрует список habit по параметру color
     */
    fun byColor(habits: MutableList<Habit>, color: Int): MutableList<Habit> {

        return (habits.filter { it.color == color }).toMutableList()
    }
}
