package com.salexey.datamodels.exceptions

import com.salexey.datamodels.exceptions.code4xx.ResponseCode400Exception
import com.salexey.datamodels.exceptions.code4xx.ResponseCode401Exception
import com.salexey.datamodels.exceptions.code5xx.ResponseCode500Exception

open class ResponseCodeException(message:String): Exception(message){
    companion object{

        /**
         * выкидывыает ResponseCodeXXXException, в зависимости от полученного кода.
         * если исключения с таким кодом нет, выкидывает общее исключение
         */
        fun throwByCode(code: Int, message: String): ResponseCodeException {

            when(code){
                400 -> {
                    return ResponseCode400Exception("$code $message")
                }

                401 -> {
                    return ResponseCode401Exception("$code $message")
                }

                500-> {
                    return ResponseCode500Exception("$code $message")
                }

                else -> {
                    return ResponseCodeException("unknown code: $code $message")
                }
            }

        }
    }
}
