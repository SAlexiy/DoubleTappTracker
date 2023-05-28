package com.salexey.network.ktor

import com.salexey.doubletapptracker.consts.network.Urls
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class Ktor {
    val client = HttpClient(CIO){
        defaultRequest {
            url(Urls.base)
            header("Authorization", "83e312c9-b9a2-4418-bef1-b26a857c0c73")
            header("Content-Type", ContentType.Application.Json.toString())
        }

        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }
}