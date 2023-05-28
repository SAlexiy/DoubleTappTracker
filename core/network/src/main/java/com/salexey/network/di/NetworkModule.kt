package com.salexey.network.di

import com.salexey.network.ktor.HabitRepositoryKtor
import com.salexey.network.ktor.Ktor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {


    @Singleton
    @Provides
    fun provideHabitRepository(ktor: Ktor): HabitRepositoryKtor {
        val client = ktor.client

        return HabitRepositoryKtor(ktor)
    }

    @Singleton
    @Provides
    fun provideKtorClient(): Ktor {

        return Ktor()
    }
}