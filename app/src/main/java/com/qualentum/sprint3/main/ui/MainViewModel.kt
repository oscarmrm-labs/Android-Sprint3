package com.qualentum.sprint3.main.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.common.ui.GetWeatherState.getWeatherState
import com.qualentum.sprint3.main.data.model.nextdays.DailyForecastResponse
import com.qualentum.sprint3.main.data.model.nextdays.DailyLists
import com.qualentum.sprint3.main.data.model.today.CurrentDay
import com.qualentum.sprint3.main.data.model.today.CurrentDayResponse
import com.qualentum.sprint3.main.data.model.today.CurrentWeather
import com.qualentum.sprint3.main.data.repository.MainMeteoAPIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val latitude: Double, val longitude: Double) : ViewModel() {
    private val apiService: MainMeteoAPIService = OpenMeteoClient.mainService
    private val forecastDaysConst: Int = 7

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

    init {
        viewModelScope.launch {
            loadingMutableState.value = true
            fetchCurrentWeatherData(
                currentParams = "temperature_2m,is_day,rain,showers,snowfall",
                dailyParams = "temperature_2m_max,temperature_2m_min,sunrise,sunset",
                forecastDays = "1"
            )
            fetchDailyWeatherData(
                dailyParams = "temperature_2m_max,temperature_2m_min,rain_sum,showers_sum,snowfall_sum"
            )
            loadingMutableState.value = false
        }
    }

    private fun fetchCurrentWeatherData(currentParams: String, dailyParams: String, forecastDays: String) {
        apiService.getCurrentWeather(latitude, longitude, currentParams, dailyParams, forecastDays).enqueue(object :
            Callback<CurrentDayResponse> {
            override fun onResponse(
                call: Call<CurrentDayResponse>,
                response: Response<CurrentDayResponse>
            ) {
                if (response.isSuccessful) {
                    currentWearherMutableState.value = response.body()?.current
                    dayWeatherMutableState.value = response.body()?.currentDay
                    weatherStateMutableState.value = getWeatherState(
                        currentWearherMutableState.value?.rain,
                        response.body()?.current?.showers,
                        response.body()?.current?.snowfall,
                        0,
                    )
                } else {
                    Log.w("TAG", "Error en la solicitud")
                }
            }

            override fun onFailure(call: Call<CurrentDayResponse>, throwable: Throwable) {
                Log.w("TAG", "onFailure: ERROR => ${throwable.message}")
            }
        })
    }

    private fun fetchDailyWeatherData(dailyParams: String) {
        apiService.getDaily(latitude, longitude, dailyParams, forecastDaysConst).enqueue(object : Callback<DailyForecastResponse> {
            override fun onResponse(call: Call<DailyForecastResponse>, response: Response<DailyForecastResponse>) {
                if (response.isSuccessful) {
                    dailyWeatherMutableState.value = response.body()?.dailyLists
                } else {
                    Log.w("TAG", "Error en la solicitud")
                }
            }

            override fun onFailure(call: Call<DailyForecastResponse>, throwable: Throwable) {
                Log.w("TAG", "onFailure: ERROR => ${throwable.message}")
            }
        })
    }

}