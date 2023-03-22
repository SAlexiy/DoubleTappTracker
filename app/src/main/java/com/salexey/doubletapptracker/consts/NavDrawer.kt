package com.salexey.doubletapptracker.consts

import com.salexey.doubletapptracker.R

sealed class NavDrawer(val title: String, val int: Int) {

    companion object{

        val navDrawerList = listOf<NavDrawer>(
            HabitList,
            About
        )
    }

    object HabitList : NavDrawer("Habit list",R.id.habitListFragment)
    object About : NavDrawer("About", R.id.aboutAppFragment)
}
