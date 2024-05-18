package com.qualentum.sprint3.main.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.qualentum.sprint3.R
import com.qualentum.sprint3.databinding.ActivityMainBinding
import com.qualentum.sprint3.detail.ui.DetailDay
import com.qualentum.sprint3.main.data.model.nextdays.DailyForecastResponse
import com.qualentum.sprint3.main.data.model.nextdays.DailyLists
import com.qualentum.sprint3.main.data.model.nextdays.OneDay
import com.qualentum.sprint3.main.data.model.today.CurrentDay
import com.qualentum.sprint3.main.data.model.today.CurrentDayResponse
import com.qualentum.sprint3.main.data.model.today.CurrentWeather
import com.qualentum.sprint3.main.data.repository.MeteoAPIService
import com.qualentum.sprint3.main.ui.list.DayAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val TAG = "TAG"
    val latitude = 40.41
    val longitude = -3.70
    val forecastDaysConst = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transparentSystemBars()
        requestCurrentTime()
        requestDailyInfo()
    }

//region transparentSystemBars
private fun transparentSystemBars() {
    enableEdgeToEdge()
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}
//endregion

//region requestCurrentTime
    private fun requestCurrentTime() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(MeteoAPIService::class.java)

        //val latitud = "20.0"
        //val longitud = "12.0"
        val currentParams = "temperature_2m,is_day,rain,showers,snowfall"
        val dailyParams = "temperature_2m_max,temperature_2m_min,sunrise,sunset"
        val forecastDays = "1"
        apiService.getCurrentWeather(latitude, longitude, currentParams, dailyParams, forecastDays).enqueue(object : Callback<CurrentDayResponse> {
            override fun onResponse(call: Call<CurrentDayResponse>, response: Response<CurrentDayResponse>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "onResponse: ${response.raw()}")
                    Log.w(TAG, "onResponse: DATOS =>  ${response.body()}")
                    Log.w(TAG, "onResponse: DATOS ACTUALES =>  ${response.body()?.current}")
                    val currentWeather: CurrentWeather? = response.body()?.current
                    Log.i(TAG, "onResponse: OBJETO => ${currentWeather}")
                    binding.textView2.text = currentWeather?.temperature.toString()
                    binding.textView3.text = currentWeather?.rain.toString()
                    val todayWeather: CurrentDay? = response.body()?.currentDay
                    Log.i(TAG, "onResponse: OBJETO => ${todayWeather}")
                    binding.textView4.text = "Min.: ${todayWeather?.temperatureMin?.get(0).toString()}"
                    binding.textView5.text = "Max.: ${todayWeather?.temperatureMax?.get(0).toString()}"
                    binding.textView6.text = todayWeather?.sunrise?.get(0)
                    binding.textView7.text = todayWeather?.sunset?.get(0)

                } else {

                    Log.w(TAG, "Error en la solicitud")
                }
            }

            override fun onFailure(call: Call<com.qualentum.sprint3.main.data.model.today.CurrentDayResponse>, throwable: Throwable) {
                Log.w(TAG, "onFailure: ERROR => ${throwable.message}")
            }
        })
    }

//endregion

//region RequestDailyInfo
    fun requestDailyInfo() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(MeteoAPIService::class.java)

        //val latitud = 52.52
        //val longitud = 13.41
        val dailyparams = "temperature_2m_max,temperature_2m_min,rain_sum,showers_sum,snowfall_sum"
        apiService.getDaily(latitude, longitude, dailyparams, forecastDaysConst).enqueue(object : Callback<DailyForecastResponse> {
            override fun onResponse(call: Call<DailyForecastResponse>, response: Response<DailyForecastResponse>) {
                if (response.isSuccessful) {
                    val daily: DailyLists? = response.body()?.dailyLists
                    setUprDailyRecyclerView(daily)
                } else {
                    Log.w(TAG, "Error en la solicitud")
                }
            }

            override fun onFailure(call: Call<DailyForecastResponse>, throwable: Throwable) {
                Log.w(TAG, "onFailure: ERROR => ${throwable.message}")
            }
        })
    }

    private fun setUprDailyRecyclerView(daily: DailyLists?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter =
            DayAdapter(inflateDaily(daily)) { oneDay -> changeScreen(oneDay) }
    }

    fun inflateDaily(daily: DailyLists?): MutableList<OneDay> {
        var dailyInfo: MutableList<OneDay> = ArrayList()
        for (i in 0..forecastDaysConst - 1) {
            dailyInfo.add(
                OneDay(
                    daily?.time?.get(i),
                    daily?.temperatureMin?.get(i),
                    daily?.temperatureMax?.get(i),
                    daily?.rainSum?.get(i),
                    daily?.showersSum?.get(i),
                    daily?.snowfallSum?.get(i)
                )
            )
        }
        return dailyInfo
    }

    private fun changeScreen(dayInfo: OneDay) {
        val i = Intent(this, DetailDay::class.java).apply {
            putExtra("dayInfo", dayInfo.time)
        }
        startActivity(i)
    }
    //endregion

}
