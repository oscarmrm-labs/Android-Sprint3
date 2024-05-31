package com.qualentum.sprint3.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualentum.sprint3.detail.data.mappers.DetailWeather
import com.qualentum.sprint3.detail.domain.usecases.GetDetailWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(getDetailWeatherUseCase: GetDetailWeatherUseCase): ViewModel() {

    private val detailDayMutableState: MutableStateFlow<DetailWeather?> = MutableStateFlow(DetailWeather(ArrayList(emptyList())))
    val detailDayState: StateFlow<DetailWeather?> = detailDayMutableState

    private val loadingMutableState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> = loadingMutableState

    private val errorMutableState = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = errorMutableState

    init {
        viewModelScope.launch(Dispatchers.IO){
            loadingMutableState.value = true
            getDetailWeatherUseCase.invoke().onSuccess {
                detailDayMutableState.value = it
            }.onFailure {
                errorMutableState.value = true
            }
            loadingMutableState.value = false
        }
    }


}