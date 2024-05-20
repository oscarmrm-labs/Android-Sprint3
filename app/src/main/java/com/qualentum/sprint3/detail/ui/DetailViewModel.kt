package com.qualentum.sprint3.detail.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.detail.data.model.day.DayDetailLists
import com.qualentum.sprint3.detail.data.model.day.DayDetailResponse
import com.qualentum.sprint3.detail.data.repository.DetailMeteoAPIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(val latitude: Double, val longitude: Double): ViewModel() {
    private val apiService: DetailMeteoAPIService = OpenMeteoClient.detailService

    private val detailDayMutableState: MutableStateFlow<DayDetailLists?> = MutableStateFlow(DayDetailLists(emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()))
    val detailDayState: StateFlow<DayDetailLists?> = detailDayMutableState

    init {
        fetchDetailData()
    }

    private fun fetchDetailData() {
        val daily = "temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset," +
                "uv_index_max," +
                ",," + // FIXME: El campo " rain_sum " da error en la solicitud
                "showers_sum,snowfall_sum"

        val startDay = ""
        val endDay = ""
        apiService.getDayDetail(latitude, longitude, daily, startDay, endDay).enqueue(object :
            Callback<DayDetailResponse> {
            override fun onResponse(call: Call<DayDetailResponse>, response: Response<DayDetailResponse>) {
                if (response.isSuccessful) {
                    detailDayMutableState.value = response.body()?.dayDetailLists
                } else {
                    Log.w("TAG", "Error en la solicitud")
                }
            }

            override fun onFailure(call: Call<DayDetailResponse>, throwable: Throwable) {
                Log.w("TAG", "onFailure: ERROR => ${throwable.message}")
            }
        })
    }
}