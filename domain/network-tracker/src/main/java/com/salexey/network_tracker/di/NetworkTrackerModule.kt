package com.salexey.network_tracker.di

import android.content.Context
import com.salexey.network_tracker.network.NetworkStatusTracker
import dagger.Module
import dagger.Provides

@Module
class NetworkTrackerModule {

    @Provides
    fun provideNetworkStatusTracker(context: Context): NetworkStatusTracker {
        return NetworkStatusTracker(context)
    }

}
