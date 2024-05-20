package com.qualentum.sprint3.main.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.main.data.model.nextdays.DailyForecastResponse
import com.qualentum.sprint3.main.data.model.nextdays.DailyLists
import com.qualentum.sprint3.main.data.model.today.CurrentDay
import com.qualentum.sprint3.main.data.model.today.CurrentDayResponse
import com.qualentum.sprint3.main.data.model.today.CurrentWeather
import com.qualentum.sprint3.main.data.repository.MeteoAPIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(): ViewModel() {
    private val apiService: MeteoAPIService = OpenMeteoClient.mainService
    val forecastDaysConst: Int = 7

    private val loadingMutableState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> get() = loadingMutableState

    private var currentWearherMutableState: MutableStateFlow<CurrentWeather?> = MutableStateFlow(CurrentWeather("", 0, 0.0, 0, 0.0, 0.0, 0.0))
    val currentWeatherState: MutableStateFlow<CurrentWeather?> = currentWearherMutableState
    private var dayWeatherMutableState: MutableStateFlow<CurrentDay?> = MutableStateFlow(CurrentDay(emptyList(), emptyList(), emptyList(), emptyList(), emptyList()))
    val dayWeatherState: MutableStateFlow<CurrentDay?> = dayWeatherMutableState

    private var dailyWeatherMutableState: MutableStateFlow<DailyLists?> = MutableStateFlow(DailyLists(emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()))
    val listsDayWeatherState: MutableStateFlow<DailyLists?> = dailyWeatherMutableState

    init {
        viewModelScope.launch {
            fetchCurrentWeatherData()
            fetchDailyWeatherData()
        }
    }

    fun fetchCurrentWeatherData() {
//        val apiService = OpenMeteoClient.mainService
        val latitude = 20.0
        val longitude = 12.0
        val currentParams = "temperature_2m,is_day,rain,showers,snowfall"
        val dailyParams = "temperature_2m_max,temperature_2m_min,sunrise,sunset"
        val forecastDays = "1"
        apiService.getCurrentWeather(latitude, longitude, currentParams, dailyParams, forecastDays).enqueue(object :
            Callback<CurrentDayResponse> {
            override fun onResponse(
                call: Call<CurrentDayResponse>,
                response: Response<CurrentDayResponse>
            ) {
                if (response.isSuccessful) {
                    currentWearherMutableState.value = response.body()?.current
                    dayWeatherMutableState.value = response.body()?.currentDay
                } else {
                    Log.w("TAG", "Error en la solicitud")
                }
            }

            override fun onFailure(call: Call<CurrentDayResponse>, throwable: Throwable) {
                Log.w("TAG", "onFailure: ERROR => ${throwable.message}")
            }
        })
    }

    private fun fetchDailyWeatherData() {
        val latitude = 52.52
        val longitude = 13.41
        val dailyparams = "temperature_2m_max,temperature_2m_min,rain_sum,showers_sum,snowfall_sum"
        apiService.getDaily(latitude, longitude, dailyparams, forecastDaysConst).enqueue(object : Callback<DailyForecastResponse> {
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