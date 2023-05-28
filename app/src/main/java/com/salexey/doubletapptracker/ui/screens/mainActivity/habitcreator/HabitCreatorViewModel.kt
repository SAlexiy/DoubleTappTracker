package com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salexey.datamodels.habit.Habit
import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.features.flow.ValueStateFlow
import com.salexey.habit_manager.HabitRepository
import kotlinx.coroutines.launch
import java.util.*

class HabitCreatorViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

    val habit = ValueStateFlow(Habit(
        uid = "",
        color = 0,
        count = 0,
        date = 0,
        description = "",
        doneDates = mutableListOf(),
        frequency = 0,
        priority = 0,
        title = "",
        type = 0
    ))

    /**
     * Обнавляет habit на текщие значения в view model
     */
    fun updateHabitParams() {
        habit.setValue(
            Habit(
                uid = habit.getValue().uid,
                title = name.getValue(),
                description = description.getValue(),
                frequency = frequency.getValue(),
                priority = priority.getValue(),
                type = type.getValue(),
                color = color.getValue(),
                date = 0,
                doneDates = mutableListOf(),
                count = 0
            )
        )
    }


    val name = ValueStateFlow("")
    val description = ValueStateFlow("")
    val frequency = ValueStateFlow(0)
    val isFrequencySpinnerExpanded = ValueStateFlow(false)

    val priority = ValueStateFlow(0)
    val isPrioritySpinnerExpanded = ValueStateFlow(false)

    val type = ValueStateFlow(0)
    val color = ValueStateFlow(Color.Transparent.toArgb())


    /**
     * обновление значение всех параметров
     */
    fun setParams(habit: Habit) {
        this.habit.setValue(habit)
        name.setValue(habit.title)
        description.setValue(habit.description)
        frequency.setValue(habit.frequency)
        priority.setValue(habit.priority)
        color.setValue(habit.color)
        type.setValue( habit.type )
    }

    /**
     * Возвращает TypeValue с значением value.
     * Если такого нет, возвращает TypeValue.POSITIVE
     */
    fun getValueType(value: Int): TypeValue {
        return try {
            TypeValue.getTypeByValue(value)
        } catch (e: IllegalArgumentException){
            TypeValue.POSITIVE
        }
    }

    /**
     * удаляет привычку
     */
    fun deleteHabit(){
        viewModelScope.launch {

            habitRepository.deleteHabit(habit.getValue())

        }
    }

    /**
     * создаёт привычку
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertHabit(){
        viewModelScope.launch {

            habitRepository.insertHabit(habit.getValue())
        }
    }

    /**
     * обнавляет привычку
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateHabit(){
        viewModelScope.launch {

            habitRepository.updateHabit(habit.getValue())
        }
    }

    companion object{
        private var habitCreatorViewModel: HabitCreatorViewModel? = null

        /**
         * Возвращает экземпляр HabitCreatorViewModel.
         *
         * Если модель до этого не использовалась, создаётся новый экземпляр,
         * иначе возвращает предыдущий
         *
         */
        fun getViewModel(habitRepository: HabitRepository): HabitCreatorViewModel {

            if (habitCreatorViewModel == null){
                habitCreatorViewModel = createViewModel(habitRepository)
            }

            return habitCreatorViewModel!!
        }


        private fun createViewModel(habitRepository: HabitRepository): HabitCreatorViewModel {
            return HabitCreatorViewModel(habitRepository)
        }
    }
}
