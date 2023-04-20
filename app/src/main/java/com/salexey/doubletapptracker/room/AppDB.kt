package com.salexey.doubletapptracker.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salexey.doubletapptracker.datamodel.Habit

@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun habitDao(): HabitDao

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
                .allowMainThreadQueries()
                .build()
    }
}
