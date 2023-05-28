package com.salexey.roomdb.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salexey.datamodels.changes.ChangeUnit
import com.salexey.datamodels.habit.Habit
import com.salexey.roomdb.room.changes.ChangesDao
import com.salexey.roomdb.room.habit.HabitDao

@Database(entities = [Habit::class, ChangeUnit::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun changesDao(): ChangesDao

    companion object DatabaseBuilder {
        private var INSTANCE: AppDB? = null

        fun getInstance(context: Context): AppDB {
            if (INSTANCE == null) {
                synchronized(AppDB::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java,
                "app-db"
            )
                .build()
    }
}
