package com.salexey.changes_manager.changes

import com.salexey.datamodels.changes.ChangeUnit
import com.salexey.datamodels.habit.Habit
import com.salexey.datamodels.habit.HabitDone
import com.salexey.datamodels.habit.HabitPut
import com.salexey.datamodels.habit.HabitUID
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

class ChangesConverter {

    fun convert(habitPut: HabitPut): ChangeUnit {
        return ChangeUnit(
            UUID.randomUUID().toString(),
            0,
            Json.encodeToString(habitPut))
    }

    fun convert(habit: Habit): ChangeUnit {
        return ChangeUnit(
            UUID.randomUUID().toString(),
            1,
            Json.encodeToString(habit))
    }

    fun convert(habitUID: HabitUID): ChangeUnit {
        return ChangeUnit(
            UUID.randomUUID().toString(),
            2,
            Json.encodeToString(habitUID))
    }

    fun convert(habitDone: HabitDone): ChangeUnit {
        return ChangeUnit(
            UUID.randomUUID().toString(),
            3,
            Json.encodeToString(habitDone))
    }
}