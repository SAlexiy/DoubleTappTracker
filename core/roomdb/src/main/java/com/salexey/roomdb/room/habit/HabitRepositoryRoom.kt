package com.salexey.roomdb.room.habit

import com.salexey.datamodels.habit.Habit
import com.salexey.datamodels.habit.HabitDone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HabitRepositoryRoom(private val habitDao: HabitDao) {

    suspend fun getHabitsFlow(): Flow<MutableList<Habit>> {
        return habitDao.getAllAsFlow()
    }

    suspend fun getHabits(): MutableList<Habit> = withContext(Dispatchers.IO) {
        return@withContext habitDao.getAllAsList()
    }

    suspend fun getHabit(habitId: String): Habit = withContext(Dispatchers.IO) {
        return@withContext habitDao.get(habitId)
    }

    suspend fun insertHabit(habit: Habit) = withContext(Dispatchers.IO) {
        return@withContext try {
            habitDao.insert(habit)
        }catch (e : Exception){
            habitDao.update(habit)
        }
    }

    suspend fun updateHabit(habit: Habit) = withContext(Dispatchers.IO) {
        return@withContext habitDao.update(habit)
    }

    suspend fun deleteHabit(habit: Habit) = withContext(Dispatchers.IO) {
        return@withContext habitDao.delete(habit)
    }

    suspend fun updateHabitDone(value: HabitDone) = withContext(Dispatchers.IO) {
        val habit = getHabit(value.uid)


        habit.doneDates.add(value.date)


        return@withContext habitDao.update(habit = habit)
    }
}
