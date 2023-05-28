package com.salexey.network.ktor

import com.salexey.datamodels.habit.Habit
import com.salexey.datamodels.habit.HabitDone
import com.salexey.datamodels.habit.HabitPut
import com.salexey.datamodels.habit.HabitUID
import com.salexey.doubletapptracker.consts.network.Urls
import io.ktor.client.request.*
import java.util.logging.Logger
import javax.inject.Inject

class HabitRepositoryKtor @Inject constructor(
    private val ktor: Ktor
) {
    private val logger = Logger.getLogger("HabitRepository")
    private val client = ktor.client

    /**
     *  Получает с сервера список Habit, в случае успешного запроса
     *  и выкидывает ResponseCodeXXXException в случае ошибки.
     */
    suspend fun getHabits(): MutableList<Habit> {
        logger.info("getHabits")
        val response = client.get(Urls.getHabits)

        return CheckResponseStatus.getObject<MutableList<Habit>>(response, logger)
    }


    /**
     *  Отправляет в бд новый Habit, в случае успешного запроса
     *  и выкидывает ResponseCodeXXXException в случае ошибки.
     */
    suspend fun putHabit(habitPut: HabitPut): HabitUID {
        logger.info("putHabits")
        val response = client.put(Urls.putHabits){
            setBody(habitPut)
        }

        return CheckResponseStatus.getObject<HabitUID>(response, logger)
    }

    /**
     *  Обновляет в бд Habit, в случае успешного запроса
     *  и выкидывает ResponseCodeXXXException в случае ошибки.
     */
    suspend fun putHabit(habit: Habit): HabitUID {
        logger.info("putHabits")
        val response = client.put(Urls.putHabits){
            setBody(habit)
        }

        return CheckResponseStatus.getObject<HabitUID>(response, logger)
    }


    /**
     *  Изменяет Habit, в случае успешного запроса
     *  и выкидывает ResponseCodeXXXException в случае ошибки.
     */
    suspend fun postHabit(habitDone: HabitDone) {
        logger.info("postHabits")
        val response = client.post(Urls.postHabits){
            setBody(habitDone)
        }

        CheckResponseStatus.getStatus(response, logger)

    }


    /**
     *  Удаляет Habit по uid, в случае успешного запроса
     *  и выкидывает ResponseCodeXXXException в случае ошибки.
     */
    suspend fun deleteHabit(habitUID: HabitUID){
        logger.info("deleteHabits")
        val response = client.delete(Urls.deleteHabits){

            //contentType(ContentType.Application.Json)
            setBody(habitUID)
        }

        CheckResponseStatus.getStatus(response, logger)

    }
}
