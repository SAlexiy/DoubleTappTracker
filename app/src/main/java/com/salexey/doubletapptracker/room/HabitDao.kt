package com.salexey.doubletapptracker.room

import androidx.room.*
import com.salexey.doubletapptracker.datamodel.Habit
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с таблицей habit
 */
@Dao
interface HabitDao {

    /**
     * получает все записи из таблицы
     */
    @Query("SELECT * FROM habit")
    fun getAll(): Flow<MutableList<Habit>>

    /**
     * получает все записи из таблицы, в которых type равен параметру
     */
    @Query("SELECT * FROM habit WHERE type = :type")
    fun getHabitsByType(type: String): Flow<MutableList<Habit>>

    /**
     * получает habit по id
     */
    @Query("SELECT * FROM habit WHERE habitId = :habitId")
    fun get(habitId: String): Flow<Habit>

    /**
     * записывает в таблицу habit
     */
    @Insert
    suspend fun insert(habit: Habit)

    /**
     * обновляет habit
     */
    @Update
    suspend fun update(habit: Habit)

    /**
     * удаляет habit
     */
    @Delete
    suspend fun delete(habit: Habit)

}