package com.salexey.changes_manager.changes

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.salexey.datamodels.changes.ChangeUnit
import com.salexey.datamodels.consts.sharedpreference.SharedPreferenceKeys
import com.salexey.datamodels.habit.Habit
import com.salexey.datamodels.habit.HabitDone
import com.salexey.datamodels.habit.HabitPut
import com.salexey.datamodels.habit.HabitUID
import com.salexey.network.ktor.HabitRepositoryKtor
import com.salexey.roomdb.room.changes.ChangesRepositoryRoom
import com.salexey.roomdb.room.habit.HabitRepositoryRoom
import com.salexey.shared_preference.preference.SharedPreferencesStorage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ChangesRepository(
    private val changesRepositoryRoom: ChangesRepositoryRoom,
    private val habitRepositoryRoom: HabitRepositoryRoom,
    private val habitRepositoryKtor: HabitRepositoryKtor,
    private val sharedPreferences: SharedPreferencesStorage
) {

    var changesConverter = ChangesConverter()


    suspend fun checkSynch(): Boolean{
        val serverList = habitRepositoryKtor.getHabits()
        val clientList = habitRepositoryRoom.getHabits()

        return !(serverList.containsAll(clientList) && clientList.containsAll(serverList))
    }

    @SuppressLint("CommitPrefEdits")
    suspend fun synchChangeToServer(){
        for (changeUnit in getChanges()){

            when (changeUnit.type){
                0 ->{
                    habitRepositoryKtor.putHabit(
                        Json.decodeFromString<HabitPut>(changeUnit.data)
                    )
                }
                1 ->{
                    habitRepositoryKtor.putHabit(
                        Json.decodeFromString<Habit>(changeUnit.data)
                    )
                }
                2 ->{
                    habitRepositoryKtor.deleteHabit(
                        Json.decodeFromString<HabitUID>(changeUnit.data)
                    )
                }
                3 ->{
                    habitRepositoryKtor.postHabit(
                        Json.decodeFromString<HabitDone>(changeUnit.data)
                    )
                }
            }

            changesRepositoryRoom.deleteChange(changeUnit)
        }
        sharedPreferences.setValue(SharedPreferenceKeys.IS_SYNCH, true)
    }


    suspend fun synchChangeToClient(){
        val serverList = habitRepositoryKtor.getHabits()
        val clientList = habitRepositoryRoom.getHabits()

        val serverListCopy = serverList.toMutableList()
        for (habit in serverListCopy){

            if (habit in clientList){
                serverList.remove(habit)
                clientList.remove(habit)
            } else {
                habitRepositoryRoom.insertHabit(habit)

                serverList.remove(habit)
                clientList.remove(habit)
            }

        }

        for (habit in clientList){
            habitRepositoryRoom.deleteHabit(habit)
        }

    }

    private suspend fun getChanges(): List<ChangeUnit>{
        return changesRepositoryRoom.getChanges()
    }

    suspend fun addChange(changeUnit: ChangeUnit){
        changesRepositoryRoom.insertChange(changeUnit)
    }
}