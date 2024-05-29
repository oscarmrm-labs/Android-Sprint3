package com.qualentum.sprint3.detail.data.repository.remote

import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.detail.data.model.day.DayDetailResponse

class DetailRepository(val day: String, val latitude: Double, val longitude: Double, val paramsDaily: String) {
    private val apiService: DetailMeteoAPIService = OpenMeteoClient.detailService

    suspend fun fetchDetailData2(): Result<DayDetailResponse> {
        return try {
            val response = apiService.getDayDetail(latitude, longitude, paramsDaily, day, day)
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}