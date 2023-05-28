package com.salexey.roomdb.room.changes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.salexey.datamodels.changes.ChangeUnit

@Dao
interface ChangesDao {
    /**
     * получает все записи из таблицы
     */
    @Query("SELECT * FROM changes")
    suspend fun getAll(): List<ChangeUnit>


    /**
     * записывает в таблицу changes
     */
    @Insert
    suspend fun insert(changeUnit: ChangeUnit)

    @Delete
    suspend fun delete(changeUnit: ChangeUnit)
}