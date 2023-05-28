package com.salexey.roomdb.room.changes

import com.salexey.datamodels.changes.ChangeUnit

class ChangesRepositoryRoom(private val changesDao: ChangesDao) {

    suspend fun getChanges(): List<ChangeUnit> {
        return changesDao.getAll()
    }

    suspend fun insertChange(changeUnit: ChangeUnit){
        changesDao.insert(changeUnit)
    }

    suspend fun deleteChange(changeUnit: ChangeUnit){
        changesDao.delete(changeUnit)
    }
}