package com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.salexey.doubletapptracker.datamodel.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HabitCreatorViewModel() : ViewModel() {

    //параметр для сохранения привычки
    private val _habit = MutableStateFlow(Habit(habitId = ""))
    val habit = _habit.asStateFlow()
    fun setHabit(habit: Habit) {
        _habit.value = habit
    }

    fun updateHabit() {
        setHabit(Habit(
            habitId = habit.value.habitId,
            name = name.value,
            description = description.value,
            periodicity = periodicity.value,
            priority = priority.value,
            type = type.value,
            color = color.value,
        ))
    }


    //параметр для записи названия
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    fun setName(name: String) {
        _name.value = name
    }

    //параметр для записи описания
    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()
    fun setDescription(description: String) {
        _description.value = description
    }

    //параметр для записи периодичности
    private val _periodicity = MutableStateFlow("")
    val periodicity = _periodicity.asStateFlow()
    fun setPeriodicity(periodicity: String) {
        _periodicity.value = periodicity
    }


    //параметры для выбора приоритета
    private val _priority = MutableStateFlow(0)
    val priority = _priority.asStateFlow()
    fun setPriority(priority: Int) {
        _priority.value = priority
    }

    private val _spinnerExpanded = MutableStateFlow(false)
    val spinnerExpanded = _spinnerExpanded.asStateFlow()
    fun changeSpinnerExpanded(spinnerExpanded: Boolean) {
        _spinnerExpanded.value = spinnerExpanded
    }

    //параметры для выбора типа
    private val _type = MutableStateFlow("")
    val type = _type.asStateFlow()
    fun setType(type: String) {
        _type.value = type
    }

    //параметры для выбора цвета
    private val _color = MutableStateFlow(Color.Transparent.toArgb())
    val color = _color.asStateFlow()
    fun setColor(color: Int) {
        _color.value = color
    }


    //обновление всех параметров
    fun setParams(habit: Habit) {
        setHabit(habit)
        setName(habit.name)
        setDescription(habit.description)
        setPeriodicity(habit.periodicity)
        setPriority(habit.priority)
        setColor(habit.color)
        setType(habit.type)
    }
}