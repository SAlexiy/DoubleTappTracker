package com.salexey.doubletapptracker.room

import androidx.room.*
import com.salexey.doubletapptracker.datamodel.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    suspend fun getAll(): MutableList<Habit>

    @Query("SELECT * FROM habit WHERE habitId = :habitId")
    suspend fun get(habitId: String): Habit

    @Insert
    suspend fun insertAll(habits: MutableList<Habit>)

    @Insert
    suspend fun insert(habit: Habit)

    @Update
    suspend fun update(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)
}