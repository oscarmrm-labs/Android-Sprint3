package com.qualentum.sprint3.detail.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.detail.data.model.day.DayDetailLists
import com.qualentum.sprint3.detail.data.model.day.DayDetailResponse
import com.qualentum.sprint3.detail.data.repository.DetailMeteoAPIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(val day: String, val latitude: Double, val longitude: Double): ViewModel() {
    private val apiService: DetailMeteoAPIService = OpenMeteoClient.detailService

    private val detailDayMutableState: MutableStateFlow<DayDetailLists?> = MutableStateFlow(DayDetailLists(emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()))
    val detailDayState: StateFlow<DayDetailLists?> = detailDayMutableState

    private val loadingMutableState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> = loadingMutableState

    private val errorMutableState = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = errorMutableState

    init {
        viewModelScope.launch{
            loadingMutableState.value = true
            fetchDetailData2(
                daily = "temperatuUUre_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,uv_index_max,rain_sum,showers_sum,snowfall_sum"
            ).onSuccess {
                val result = it.dayDetailLists
                detailDayMutableState.value = result
                Log.d("TAG", "FUNCIONA!!: ")
            }.onFailure {
                errorMutableState.value = true
                Log.d("TAG", "NO FUNCIONA!!: ${it}")
            }
            loadingMutableState.value = false
        }
    }

    private suspend fun fetchDetailData2(daily: String): Result<DayDetailResponse> {
        return try {
            val response = apiService.getDayDetail(latitude, longitude, daily, day, day)
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}