package com.salexey.shared_preference.di

import android.content.Context
import com.salexey.shared_preference.preference.SharedPreferencesStorage
import dagger.Module
import dagger.Provides

@Module
class SharedPreferenceModule {

    @Provides
    fun provideSharedPreferenceStorage(context: Context): SharedPreferencesStorage {
        return SharedPreferencesStorage(context)
    }

}
