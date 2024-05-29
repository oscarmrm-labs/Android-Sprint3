package com.qualentum.sprint3.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.detail.data.model.day.DayDetailLists
import com.qualentum.sprint3.detail.data.model.day.DayDetailResponse
import com.qualentum.sprint3.detail.data.repository.DetailMeteoAPIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(val day: String, val latitude: Double, val longitude: Double, val paramsDaily: String): ViewModel() {
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
            fetchDetailData2().onSuccess {
                val result = it.dayDetailLists
                detailDayMutableState.value = result
            }.onFailure {
                errorMutableState.value = true
            }
            loadingMutableState.value = false
        }
    }

    private suspend fun fetchDetailData2(): Result<DayDetailResponse> {
        return try {
            val response = apiService.getDayDetail(latitude, longitude, paramsDaily, day, day)
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}