package com.salexey.datamodels.habit

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class HabitPut(

    val color: Int,

    val count: Int,

    val date: Long,

    val description: String,

    @SerialName("done_dates")
    val doneDates: List<Long>,

    val frequency: Int,

    val priority: Int,

    val title: String,

    val type: Int,

    ){
    constructor(habit: Habit) : this(
        color = habit.color,
        count = habit.count,
        date = habit.date,
        description = habit.description,
        doneDates = habit.doneDates,
        frequency = habit.frequency,
        priority = habit.priority,
        title = habit.title,
        type = habit.type
    )
}
