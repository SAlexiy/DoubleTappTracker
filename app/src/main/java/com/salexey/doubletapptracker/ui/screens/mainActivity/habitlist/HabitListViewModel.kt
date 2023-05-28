package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.salexey.datamodels.habit.Habit
import com.salexey.doubletapptracker.features.flow.ValueStateFlow
import com.salexey.habit_manager.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.logging.Logger

class HabitListViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {
    val logger = Logger.getLogger("HabitListViewModel")


    private var habitFlow: Flow<MutableList<Habit>> = flow {  }
    val habitList = ValueStateFlow(mutableListOf<Habit>())

    val isFabView = ValueStateFlow(true)


    fun getHabits(){

        viewModelScope.launch {
            habitFlow = habitRepository.getAllHabit()
        }

        viewModelScope.launch {
            habitFlow.collect{
                habitList.setValue(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateHabitDone(habit: Habit){
        viewModelScope.launch {
            habitRepository.updateHabitDone(habit)
        }
    }



    val filterTypeValue = ValueStateFlow(0)
    val filterPriority = ValueStateFlow(-1)
    val isPrioritySpinnerExpanded = ValueStateFlow(false)

    val filterColor = ValueStateFlow(-1)

    val priority = ValueStateFlow(0)

    companion object{
        private var habitListPageViewModel: HabitListViewModel? = null

        /**
         * Возвращает экземпляр HabitListPageViewModel.
         *
         * Если модель до этого не использовалась, создаётся новый экземпляр,
         * иначе возвращает предыдущий
         *
         */
        fun getViewModel(habitRepository: HabitRepository): HabitListViewModel {

            if (habitListPageViewModel == null){
                habitListPageViewModel = createViewModel(habitRepository)
            }

            return habitListPageViewModel!!
        }


        private fun createViewModel(habitRepository: HabitRepository): HabitListViewModel {

            return HabitListViewModel(habitRepository)
        }

    }
}
