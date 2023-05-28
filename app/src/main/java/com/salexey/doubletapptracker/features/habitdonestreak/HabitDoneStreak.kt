package com.salexey.doubletapptracker.features.habitdonestreak

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.salexey.datamodels.habit.Habit
import java.time.LocalDateTime

object HabitDoneStreak {
    @RequiresApi(Build.VERSION_CODES.O)
    fun showToast(habit: Habit, context: Context) {
        var res = ""

        val frequency = habit.frequency
        val size = habit.doneDates.size


        if (size < frequency){
            
            res = lessFrequency(habit.type) + (frequency - size - 1)

        } else{
            val localDateTime = LocalDateTime.now()
            val time = ("${localDateTime.year}" +
                    "${if (localDateTime.monthValue < 10) "0" + localDateTime.monthValue else localDateTime.monthValue}" +
                    "${if (localDateTime.dayOfMonth < 10) "0" + localDateTime.dayOfMonth else localDateTime.dayOfMonth}" +
                    "000000").toLong()

            var count = 0

            for (i in size-1 downTo 0 step 1) {

                if (count > frequency) break
                if (habit.doneDates[i] < time) break

                count++
            }

            if (count > frequency){
                res = moreFrequency(habit.type)
            } else{
                res = lessFrequency(habit.frequency) + (frequency - count)
            }
        }

        
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
    }

    private fun moreFrequency(type: Int): String {
        return if (type == 0){
            "ОСТАНОВИСЬ!!!"
        } else{
            "You are breathtaking!"
        }
    }

    private fun lessFrequency(type: Int): String {
        return if (type == 0){
            "Можно выполнить ещё максимум: "
        } else{
            "Нужно выполнить ещё минимум: "
        }
    }
}