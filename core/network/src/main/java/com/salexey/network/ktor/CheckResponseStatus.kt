package com.salexey.network.ktor

import com.salexey.datamodels.exceptions.ResponseCodeException
import com.salexey.datamodels.Error
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.logging.Logger


class CheckResponseStatus {
    companion object {

        /**
         * Проверяет статус response,
         * если 200, возвращает ожидаемый T объект,
         * иначе выкидывыает ResponseCodeXXXException
         */
        suspend inline fun <reified T> getObject(response: HttpResponse, logger: Logger): T {
            logger.info("${response.status}")


            if (response.status.value == HttpStatusCode.OK.value) {

                logger.info(response.bodyAsText())

                return Json.decodeFromJsonElement<T>(
                    response.body()
                )

            } else{
                val error: Error = Json.decodeFromJsonElement<Error>(
                    response.body()
                )

                throw ResponseCodeException.throwByCode(response.status.value, error.message)
            }

        }

        suspend fun getStatus(response: HttpResponse, logger: Logger) {
            logger.info("${response.status}")


            if (response.status.value == HttpStatusCode.OK.value) {

                logger.info(response.bodyAsText())


            } else{
                val error: Error = Json.decodeFromJsonElement<Error>(
                    response.body()
                )

                throw ResponseCodeException.throwByCode(
                    response.status.value, "${error.message}\n${response.status}")
            }

        }
    }
}
