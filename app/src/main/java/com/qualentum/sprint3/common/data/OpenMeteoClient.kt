package com.qualentum.sprint3.common.data

import android.util.Log
import com.qualentum.sprint3.detail.data.repository.remote.DetailMeteoAPIService
import com.qualentum.sprint3.main.data.repository.remote.MainMeteoAPIService
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

object OpenMeteoClient {

    private const val RETRFIT_TIMEOUT_IN_SECOND = 5L
    private const val SHA256_OPEN_METEO = "FUyUZOkodJVEZGiT9iMn76FV0e37t9rsS2zvz55roBk="
    private val retrofit: Retrofit
    //val mainService: MainMeteoAPIService
    val detailService: DetailMeteoAPIService
    init {
        Log.i("TAG", "init retrofit: ")
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val certificatePinner = CertificatePinner.Builder()
            .add("api.open-meteo.com", "sha256/$SHA256_OPEN_METEO")
            .build()
        httpClient.certificatePinner(certificatePinner)

        val hostNamesAllow = listOf(
            "api.open-meteo.com"
        )

        val hostNameVerifier = HostnameVerifier { hostname, session ->
            hostname in hostNamesAllow
        }
        httpClient.hostnameVerifier(hostNameVerifier)

        httpClient
            .connectTimeout(RETRFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            .readTimeout(RETRFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            .writeTimeout(RETRFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        detailService = retrofit.create(
            DetailMeteoAPIService::class.java
        )

        Log.i("TAG", "finish retrofit: ")
    }

    fun instanceMainService(): MainMeteoAPIService {
        return retrofit.create(MainMeteoAPIService::class.java)
    }

    fun instanceDetailService(): DetailMeteoAPIService {
        return retrofit.create(DetailMeteoAPIService::class.java)
    }
}