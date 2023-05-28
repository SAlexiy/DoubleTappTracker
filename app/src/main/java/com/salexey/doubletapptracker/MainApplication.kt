package com.salexey.doubletapptracker

import android.app.Application
import com.salexey.doubletapptracker.di.ApplicationComponent
import com.salexey.doubletapptracker.di.DaggerApplicationComponent
import com.salexey.doubletapptracker.di.modules.ContextModule

class MainApplication: Application(){

    lateinit var applicationComponent: ApplicationComponent
        private set


    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(context = this))
            .build()
    }

}