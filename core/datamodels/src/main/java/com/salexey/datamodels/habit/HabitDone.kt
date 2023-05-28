package com.salexey.datamodels.habit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitDone(
    val date: Long,
    @SerialName("habit_uid")
    val uid: String,
)
