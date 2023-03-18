package com.salexey.doubletapptracker.room

import com.salexey.doubletapptracker.datamodel.Habit

class HabitRepository(private val habitDao: HabitDao) {

    suspend fun getAllHabit(): MutableList<Habit> {
        return habitDao.getAll()
    }

    suspend fun getHabit(habitId: String): Habit {
        return habitDao.get(habitId)
    }

    suspend fun insertHabit(habit: Habit) {
        return try {
            habitDao.insert(habit)
        }catch (e : Exception){
            habitDao.update(habit)
        }
    }

    suspend fun updateHabit(habit: Habit) {
        return habitDao.update(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        return habitDao.delete(habit)
    }
}