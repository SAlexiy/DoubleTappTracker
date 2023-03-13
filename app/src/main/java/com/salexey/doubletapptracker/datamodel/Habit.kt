package com.salexey.doubletapptracker.datamodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "habit")
data class Habit(

    @PrimaryKey
    var habitId: String,

    @ColumnInfo
    var name: String = "",

    @ColumnInfo
    var description: String = "",

    @ColumnInfo
    var priority: Int = 0,

    @ColumnInfo
    var type: String = "",

    @ColumnInfo
    var periodicity: String = "",

    @ColumnInfo
    var color: Int = Color.Transparent.toArgb()
): Serializable
