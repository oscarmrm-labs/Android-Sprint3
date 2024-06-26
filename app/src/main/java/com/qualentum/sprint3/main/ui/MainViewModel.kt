package com.qualentum.sprint3.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualentum.sprint3.main.data.mappers.CurrentWeather
import com.qualentum.sprint3.main.data.mappers.ListDays
import com.qualentum.sprint3.main.domain.usecases.GetCurrentWeatherUseCase
import com.qualentum.sprint3.main.domain.usecases.GetDailyWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    val getDailyWeatherUseCase: GetDailyWeatherUseCase
) : ViewModel() {

    private val loadingMutableState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> = loadingMutableState

    private val weatherStateMutableState = MutableStateFlow("sun")
    val weatherStateState: StateFlow<String> = weatherStateMutableState

    private var currentWeatherMutableState: MutableStateFlow<CurrentWeather?> = MutableStateFlow(CurrentWeather("", 0, "", "", "", "", ""))
    val currentWeatherState: StateFlow<CurrentWeather?> = currentWeatherMutableState

    private var dailyWeatherMutableState: MutableStateFlow<ListDays?> = MutableStateFlow(ListDays(ArrayList(emptyList())))
    val listsDayWeatherState: StateFlow<ListDays?> = dailyWeatherMutableState

    private val errorMutableState = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = errorMutableState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadingMutableState.value = true
            currentDay(getCurrentWeatherUseCase)
            forecastDays(getDailyWeatherUseCase)
            loadingMutableState.value = false
        }
    }

    private suspend fun currentDay(currentWeatherUseCase: GetCurrentWeatherUseCase) {
        currentWeatherUseCase.invoke().onSuccess {
            currentWeatherMutableState.value = it
        }.onFailure {
            errorMutableState.value = true
        }
    }

    private suspend fun forecastDays(dailyWeatherUseCase: GetDailyWeatherUseCase) {
        dailyWeatherUseCase.invoke().onSuccess {
            dailyWeatherMutableState.value = it
        }.onFailure {
            errorMutableState.value = true
        }
    }
}