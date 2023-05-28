package com.salexey.datamodels.changes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "changes")
data class ChangeUnit(
    @PrimaryKey
    val uid: String,
    val type: Int,
    val data: String
) {
    companion object{
        /**
         * 0 - put habitPut
         * 1 - put habit
         * 2 - delete
         * 3 - done date
         */
        val types = listOf<Int>(0,1,2,3)
    }
}
