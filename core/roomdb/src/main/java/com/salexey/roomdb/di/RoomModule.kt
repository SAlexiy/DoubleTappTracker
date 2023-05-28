package com.salexey.roomdb.di

import android.content.Context
import com.salexey.roomdb.room.AppDB
import com.salexey.roomdb.room.changes.ChangesRepositoryRoom
import com.salexey.roomdb.room.habit.HabitRepositoryRoom
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    fun provideHabitRepositoryRoom(appDB: AppDB): HabitRepositoryRoom {
        return HabitRepositoryRoom(appDB.habitDao())
    }

    @Provides
    fun provideChangesRepositoryRoom(appDB: AppDB): ChangesRepositoryRoom {
        return ChangesRepositoryRoom(appDB.changesDao())
    }


    @Singleton
    @Provides
    fun provideDB(context: Context): AppDB {
        return AppDB.getInstance(context)
    }

}
