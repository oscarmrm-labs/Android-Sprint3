package com.qualentum.sprint3.common.data

import com.qualentum.sprint3.detail.data.repository.DetailMeteoAPIService
import com.qualentum.sprint3.main.data.repository.MainMeteoAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenMeteoClient {
/*
    val serverHostName = "https://open-meteo.com"

    // TODO: Ver como obtener certificado de la página
    val certificatePinner = CertificatePinner.Builder()
        .add(serverHostName, "")
        .build()
*/

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mainService = retrofit.create(
        MainMeteoAPIService::class.java
    )

    val detailService = retrofit.create(
        DetailMeteoAPIService::class.java
    )
}