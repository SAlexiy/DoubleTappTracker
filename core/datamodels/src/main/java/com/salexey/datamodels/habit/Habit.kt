package com.salexey.datamodels.habit


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.Serializable


@kotlinx.serialization.Serializable
@TypeConverters(IntListConverters::class)
@Entity(tableName = "habit")
data class Habit(

    val color: Int,

    val count: Int,

    var date: Long,

    val description: String = "",

    @SerialName("done_dates")
    val doneDates: MutableList<Long> = mutableListOf(),

    val frequency: Int,

    val priority: Int,

    val title: String,

    val type: Int,

    @PrimaryKey
    var uid: String,
): Serializable


class IntListConverters {

    @TypeConverter
    fun stringToObject(value: String): MutableList<Long> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun objectToString(list: MutableList<Long>): String {
        return Json.encodeToString(list)
    }
}
