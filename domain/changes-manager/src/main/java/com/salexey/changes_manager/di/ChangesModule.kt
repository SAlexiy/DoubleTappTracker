package com.salexey.changes_manager.di

import com.salexey.changes_manager.changes.ChangesRepository
import com.salexey.network.ktor.HabitRepositoryKtor
import com.salexey.roomdb.room.changes.ChangesRepositoryRoom
import com.salexey.roomdb.room.habit.HabitRepositoryRoom
import com.salexey.shared_preference.preference.SharedPreferencesStorage
import dagger.Module
import dagger.Provides

@Module
class ChangesModule {

    @Provides
    fun provideChangeRepository(
        changesRepositoryRoom: ChangesRepositoryRoom,
        habitRepositoryRoom: HabitRepositoryRoom,
        habitRepositoryKtor: HabitRepositoryKtor,
        sharedPreferencesStorage: SharedPreferencesStorage
    ): ChangesRepository{
        return ChangesRepository(changesRepositoryRoom, habitRepositoryRoom,
            habitRepositoryKtor, sharedPreferencesStorage)
    }
}