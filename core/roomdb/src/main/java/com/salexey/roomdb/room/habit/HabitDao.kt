package com.salexey.roomdb.room.habit

import androidx.room.*
import com.salexey.datamodels.habit.Habit
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
    fun getAllAsFlow(): Flow<MutableList<Habit>>

    /**
     * получает все записи из таблицы
     */
    @Query("SELECT * FROM habit")
    fun getAllAsList(): MutableList<Habit>

    /**
     * получает все записи из таблицы, в которых type равен параметру
     */
    @Query("SELECT * FROM habit WHERE type = :type")
    fun getHabitsByType(type: Int): Flow<MutableList<Habit>>

    /**
     * получает habit по id
     */
    @Query("SELECT * FROM habit WHERE uid = :habitId")
    suspend fun get(habitId: String): Habit

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