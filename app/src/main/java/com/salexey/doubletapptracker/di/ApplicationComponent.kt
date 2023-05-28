package com.salexey.doubletapptracker.di

import com.salexey.changes_manager.di.ChangesModule
import com.salexey.doubletapptracker.di.modules.ContextModule
import com.salexey.doubletapptracker.ui.screens.mainActivity.MainActivity
import com.salexey.doubletapptracker.ui.screens.mainActivity.aboutapp.AboutAppFragment
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorFragment
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.HabitListFragment
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist.page.HabitListPageFragment
import com.salexey.habit_manager.HabitManagerModule
import com.salexey.network.di.NetworkModule
import com.salexey.network_tracker.di.NetworkTrackerModule
import com.salexey.network_tracker.network.NetworkStatusTracker
import com.salexey.roomdb.di.RoomModule
import com.salexey.shared_preference.di.SharedPreferenceModule
import com.salexey.shared_preference.preference.SharedPreferencesStorage
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ContextModule::class, HabitManagerModule::class,
        NetworkModule::class, RoomModule::class, ChangesModule::class,
        ChangesModule::class, NetworkTrackerModule::class, SharedPreferenceModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: HabitListFragment)
    fun inject(fragment: HabitListPageFragment)

    fun inject(fragment: HabitCreatorFragment)

    fun inject(fragment: AboutAppFragment)

    fun inject(activity: MainActivity)
}
