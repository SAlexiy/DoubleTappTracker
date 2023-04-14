package com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.datamodel.Habit
import com.salexey.doubletapptracker.features.flow.ValueStateFlow
import com.salexey.doubletapptracker.room.AppDB
import com.salexey.doubletapptracker.room.HabitRepository
import kotlinx.coroutines.launch

class HabitCreatorViewModel(
    private val appDB: AppDB
) : ViewModel() {

    private val habitRepository= HabitRepository(appDB.habitDao())
    val habit = ValueStateFlow(Habit(habitId = ""))

    /**
     * Обнавляет habit на текщие значения в view model
     */
    fun updateHabitParams() {
        habit.setValue(
            Habit(
                habitId = habit.getValue().habitId,
                name = name.getValue(),
                description = description.getValue(),
                periodicity = periodicity.getValue(),
                priority = priority.getValue(),
                type = type.getValue().value,
                color = color.getValue(),
            )
        )
    }


    val name = ValueStateFlow("")
    val description = ValueStateFlow("")
    val periodicity = ValueStateFlow("")

    val priority = ValueStateFlow(0)
    val isSpinnerExpanded = ValueStateFlow(false)

    val type = ValueStateFlow(TypeValue.POSITIVE)
    val color = ValueStateFlow(Color.Transparent.toArgb())


    /**
     * обновление значение всех параметров
     */
    fun setParams(habit: Habit, typeValue: TypeValue) {
        this.habit.setValue(habit)
        name.setValue(habit.name)
        description.setValue(habit.description)
        periodicity.setValue(habit.periodicity)
        priority.setValue(habit.priority)
        color.setValue(habit.color)
        type.setValue( typeValue )
    }

    /**
     * Возвращает TypeValue с значением value.
     * Если такого нет, возвращает TypeValue.POSITIVE
     */
    fun getValueType(value: String): TypeValue {
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
    fun insertHabit(){
        viewModelScope.launch {

            habitRepository.insertHabit(habit.getValue())
        }
    }

    /**
     * обнавляет привычку
     */
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
        fun getViewModel(context: Context): HabitCreatorViewModel {

            if (habitCreatorViewModel == null){
                habitCreatorViewModel = createViewModel(context)
            }

            return habitCreatorViewModel!!
        }


        private fun createViewModel(context: Context): HabitCreatorViewModel {
            val appDB = AppDB.getInstance(context)

            return HabitCreatorViewModel(appDB)
        }
    }
}
