package com.salexey.habit_manager

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.salexey.changes_manager.changes.ChangesRepository
import com.salexey.datamodels.consts.sharedpreference.SharedPreferenceKeys
import com.salexey.datamodels.exceptions.ResponseCodeException
import com.salexey.datamodels.exceptions.code5xx.ResponseCode500Exception
import com.salexey.datamodels.habit.Habit
import com.salexey.datamodels.habit.HabitDone
import com.salexey.datamodels.habit.HabitPut
import com.salexey.datamodels.habit.HabitUID
import com.salexey.network.ktor.HabitRepositoryKtor
import com.salexey.roomdb.room.habit.HabitRepositoryRoom
import com.salexey.shared_preference.preference.SharedPreferencesStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.logging.Logger
import javax.inject.Inject

class HabitRepository @Inject constructor(
    private var habitRepositoryKtor: HabitRepositoryKtor,
    private var habitRepositoryRoom: HabitRepositoryRoom,
    private var changesRepository: ChangesRepository,
    private var sharedPreferencesStorage: SharedPreferencesStorage,
    private var context: Context
) {
    private val logger = Logger.getLogger("HabitRepository")

    private val maxCountOfAttempt = 3
    private var countOfAttempt = 0

    suspend fun getAllHabit(): Flow<MutableList<Habit>> {
        /**
        if (countOfAttempt >= maxCountOfAttempt){
            countOfAttempt = 0

            return habitRepositoryRoom.getAllHabit()
        }

        try {
            val b = habitRepositoryKtor.getHabits()


        } catch (e: ResponseCode500Exception){
            logger.info("ResponseCode500Exception, attempt:$countOfAttempt")
            getAllHabit()
        } catch (e: ResponseCodeException){
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

            return habitRepositoryRoom.getAllHabit()
        }*/

        return habitRepositoryRoom.getHabitsFlow()
    }

    suspend fun deleteHabit(value: Habit) {
        if (countOfAttempt >= maxCountOfAttempt) {
            countOfAttempt = 0

            changesRepository.addChange(
                changesRepository.changesConverter.convert(
                    HabitUID(value.uid)
                )
            )

            sharedPreferencesStorage.setValue(SharedPreferenceKeys.IS_SYNCH, false)
            habitRepositoryRoom.deleteHabit(value)
            return
        }

        try {
            habitRepositoryKtor.deleteHabit(
                HabitUID(value.uid)
            )

        } catch (e: ResponseCode500Exception){

            logger.info("ResponseCode500Exception, attempt:$countOfAttempt")
            delay(500)
            deleteHabit(value)
            return

        } catch (e: ResponseCodeException){
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

            changesRepository.addChange(
                changesRepository.changesConverter.convert(
                    HabitUID(value.uid)
                )
            )
        }

        countOfAttempt = 0
        habitRepositoryRoom.deleteHabit(value)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateHabitDone(value: Habit) {
        val localDateTime = LocalDateTime.now()
        val time = ("${localDateTime.year}" +
                "${if (localDateTime.monthValue < 10) "0" + localDateTime.monthValue else localDateTime.monthValue}" +
                "${if (localDateTime.dayOfMonth < 10) "0" + localDateTime.dayOfMonth else localDateTime.dayOfMonth}" +
                "${if (localDateTime.hour < 10) "0" + localDateTime.hour else localDateTime.hour}" +
                "${if (localDateTime.minute < 10) "0" + localDateTime.minute else localDateTime.minute}" +
                "${if (localDateTime.second < 10) "0" + localDateTime.second else localDateTime.second}").toLong()
        val habitDone = HabitDone(time, value.uid)

        if (countOfAttempt >= maxCountOfAttempt){
            countOfAttempt = 0

            changesRepository.addChange(
                changesRepository.changesConverter.convert(
                    habitDone
                )
            )

            sharedPreferencesStorage.setValue(SharedPreferenceKeys.IS_SYNCH, false)
            habitRepositoryRoom.updateHabitDone(habitDone)
            return
        }

        try {

            habitRepositoryKtor.postHabit(
                habitDone
            )

        } catch (e: ResponseCode500Exception){

            logger.warning("ResponseCode500Exception, attempt:$countOfAttempt")
            delay(500)
            updateHabitDone(value)
            return

        } catch (e: ResponseCodeException){
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

            logger.warning("ResponseCode4xxException, attempt:$countOfAttempt")
            changesRepository.addChange(
                changesRepository.changesConverter.convert(
                    habitDone
                )
            )
        }



        countOfAttempt = 0
        habitRepositoryRoom.updateHabitDone(habitDone)
        return

    }




    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateHabit(value: Habit) {
        val localDateTime = LocalDateTime.now()
        val time = ("${localDateTime.year}" +
                "${if (localDateTime.monthValue < 10) "0" + localDateTime.monthValue else localDateTime.monthValue}" +
                "${if (localDateTime.dayOfMonth < 10) "0" + localDateTime.dayOfMonth else localDateTime.dayOfMonth}" +
                "${if (localDateTime.hour < 10) "0" + localDateTime.hour else localDateTime.hour}" +
                "${if (localDateTime.minute < 10) "0" + localDateTime.minute else localDateTime.minute}" +
                "${if (localDateTime.second < 10) "0" + localDateTime.second else localDateTime.second}").toLong()
        value.date = time

        if (countOfAttempt >= maxCountOfAttempt){
            countOfAttempt = 0

            changesRepository.addChange(
                changesRepository.changesConverter.convert(
                    value
                )
            )

            sharedPreferencesStorage.setValue(SharedPreferenceKeys.IS_SYNCH, false)
            habitRepositoryRoom.updateHabit(value)
            return
        }


        try {

            habitRepositoryKtor.putHabit(value)

        } catch (e: ResponseCode500Exception){

            logger.warning("ResponseCode500Exception, attempt:$countOfAttempt")
            delay(500)
            insertHabit(value)
            return

        } catch (e: ResponseCodeException){
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

            logger.warning("ResponseCode4xxException, attempt:$countOfAttempt")
            changesRepository.addChange(
                changesRepository.changesConverter.convert(
                    value
                )
            )
        }



        countOfAttempt = 0
        habitRepositoryRoom.updateHabit(value)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insertHabit(value: Habit) {
        val localDateTime = LocalDateTime.now()
        val time = ("${localDateTime.year}" +
                "${if (localDateTime.monthValue < 10) "0" + localDateTime.monthValue else localDateTime.monthValue}" +
                "${if (localDateTime.dayOfMonth < 10) "0" + localDateTime.dayOfMonth else localDateTime.dayOfMonth}" +
                "${if (localDateTime.hour < 10) "0" + localDateTime.hour else localDateTime.hour}" +
                "${if (localDateTime.minute < 10) "0" + localDateTime.minute else localDateTime.minute}" +
                "${if (localDateTime.second < 10) "0" + localDateTime.second else localDateTime.second}").toLong()
        value.date = time

        if (countOfAttempt >= maxCountOfAttempt){
            countOfAttempt = 0

            changesRepository.addChange(
                changesRepository.changesConverter.convert(
                    HabitPut(value)
                )
            )

            sharedPreferencesStorage.setValue(SharedPreferenceKeys.IS_SYNCH, false)
            habitRepositoryRoom.insertHabit(value)
            return
        }


        try {

            val uid = habitRepositoryKtor.putHabit(HabitPut(value))
            value.uid = uid.uid

        } catch (e: ResponseCode500Exception){

            logger.info("ResponseCode500Exception, attempt:$countOfAttempt")
            delay(500)
            insertHabit(value)
            return

        } catch (e: ResponseCodeException){
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

            changesRepository.addChange(
                changesRepository.changesConverter.convert(
                    HabitPut(value)
                )
            )
        }



        countOfAttempt = 0
        habitRepositoryRoom.insertHabit(value)
    }

}