package com.salexey.doubletapptracker.ui.screens.mainActivity.habitlist

import android.content.Context
import androidx.lifecycle.*
import com.salexey.doubletapptracker.consts.values.TypeSort
import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.datamodel.Habit
import com.salexey.doubletapptracker.features.filter.HabitFilter
import com.salexey.doubletapptracker.features.flow.ValueStateFlow
import com.salexey.doubletapptracker.features.sorter.HabitSorter
import com.salexey.doubletapptracker.room.AppDB
import com.salexey.doubletapptracker.room.HabitRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val appDB: AppDB
) : ViewModel() {

    private val habitRepository= HabitRepository(appDB.habitDao())

    var fullHabitList = listOf<Habit>()
    val habitList = ValueStateFlow(mutableListOf<Habit>())

    val isFabView = ValueStateFlow(true)

    /**
     * меняет значение habitList, на список habit, полученный из бд по значению type
     */
    fun getHabits(type: TypeValue? = null){
        viewModelScope.launch {
            if (type != null){
                habitList.setValue(habitRepository.getHabitsByType(type.value))
                fullHabitList = habitList.getValue()
            } else  {
                habitList.setValue(habitRepository.getAllHabit())
            }
        }
    }



    val sortType = ValueStateFlow<TypeSort>(TypeSort.DEFAULT)
    val filterPriority = ValueStateFlow(-1)
    val filterColor = ValueStateFlow(-1)

    fun setFilter(fullHabitList: MutableList<Habit>): List<Habit> {
        var list: List<Habit> = fullHabitList


        if (sortType.getValue() != TypeSort.DEFAULT){
            list = HabitSorter.byType(list ,sortType.getValue())
        }


        if (filterColor.getValue() != -1)
            list = HabitFilter.byColor(list, filterColor.getValue())

        if (filterPriority.getValue() != -1)
            list = HabitFilter.byPriority(list, filterPriority.getValue())


        return list
    }


    companion object{
        private var habitListPageViewModel: HabitListViewModel? = null

        /**
         * Возвращает экземпляр HabitListPageViewModel.
         *
         * Если модель до этого не использовалась, создаётся новый экземпляр,
         * иначе возвращает предыдущий
         *
         */
        fun getViewModel(context: Context): HabitListViewModel {

            if (habitListPageViewModel == null){
                habitListPageViewModel = createViewModel(context)
            }

            return habitListPageViewModel!!
        }


        private fun createViewModel(context: Context): HabitListViewModel {
            val appDB = AppDB.getInstance(context)

            return HabitListViewModel(appDB)
        }

    }
}
