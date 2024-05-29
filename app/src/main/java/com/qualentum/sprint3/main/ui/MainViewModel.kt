package com.qualentum.sprint3.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualentum.sprint3.common.ui.GetWeatherState.getWeatherState
import com.qualentum.sprint3.main.data.model.nextdays.DailyLists
import com.qualentum.sprint3.main.data.model.today.CurrentDay
import com.qualentum.sprint3.main.data.model.today.CurrentWeather
import com.qualentum.sprint3.main.data.repository.remote.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(repository: MainRepository) : ViewModel() {
    //private val forecastDaysConst: Int = 7

    private val loadingMutableState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> = loadingMutableState

    private val weatherStateMutableState = MutableStateFlow("sun")
    val weatherStateState: StateFlow<String> = weatherStateMutableState

    private var currentWearherMutableState: MutableStateFlow<CurrentWeather?> = MutableStateFlow(CurrentWeather("", 0, 0.0, 0, 0.0, 0.0, 0.0))
    val currentWeatherState: StateFlow<CurrentWeather?> = currentWearherMutableState
    private var dayWeatherMutableState: MutableStateFlow<CurrentDay?> = MutableStateFlow(CurrentDay(emptyList(), emptyList(), emptyList(), emptyList(), emptyList()))
    val dayWeatherState: StateFlow<CurrentDay?> = dayWeatherMutableState

    private var dailyWeatherMutableState: MutableStateFlow<DailyLists?> = MutableStateFlow(DailyLists(emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()))
    val listsDayWeatherState: StateFlow<DailyLists?> = dailyWeatherMutableState

    private val errorMutableState = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = errorMutableState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadingMutableState.value = true
            currentDay(repository)
            forecastDays(repository)
            loadingMutableState.value = false
        }
    }

    private suspend fun currentDay(repository: MainRepository) {
        repository.fetchCurrentWeatherData().onSuccess {
            currentWearherMutableState.value = it.current
            dayWeatherMutableState.value = it.currentDay
            weatherStateMutableState.value = getWeatherState(
                currentWearherMutableState.value?.rain,
                it.current?.showers,
                it.current?.snowfall,
                0
            )
        }.onFailure {
            errorMutableState.value = true
        }
    }

    private suspend fun forecastDays(repository: MainRepository) {
        repository.fetchDailyWeatherData().onSuccess {
            dailyWeatherMutableState.value = it.dailyLists
        }.onFailure {
            errorMutableState.value = true
        }
    }
}