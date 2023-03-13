package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.salexey.doubletapptracker.datamodel.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HabitListViewModel() : ViewModel() {
    var bundle: Bundle? = null

    private val _login = MutableStateFlow("")
    val login = _login.asStateFlow()
    fun setLogin(login: String) {
        _login.value = login
    }

    private val _habitList = MutableStateFlow(mutableListOf<Habit>())
    val habitList = _habitList.asStateFlow()
    fun setHabitList(habitList: MutableList<Habit>) {
        _habitList.value = habitList
    }


}