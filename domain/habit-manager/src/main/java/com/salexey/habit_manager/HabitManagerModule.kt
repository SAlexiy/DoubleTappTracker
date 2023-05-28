package com.salexey.habit_manager

import android.content.Context
import com.salexey.changes_manager.changes.ChangesRepository
import com.salexey.network.ktor.HabitRepositoryKtor
import com.salexey.roomdb.room.habit.HabitRepositoryRoom
import com.salexey.shared_preference.preference.SharedPreferencesStorage
import dagger.Module
import dagger.Provides

@Module
class HabitManagerModule {

    @Provides
    fun provideHabitManager(
        habitRepositoryKtor: HabitRepositoryKtor,
        habitRepositoryRoom: HabitRepositoryRoom,
        changesRepository: ChangesRepository,
        sharedPreferencesStorage: SharedPreferencesStorage,
        context: Context
    ): HabitRepository {
        return HabitRepository(habitRepositoryKtor, habitRepositoryRoom,
            changesRepository, sharedPreferencesStorage, context)
    }


}