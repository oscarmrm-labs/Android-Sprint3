package com.qualentum.sprint3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.qualentum.sprint3.databinding.ActivityMainBinding
import com.qualentum.sprint3.list.DayAdapter
import com.qualentum.sprint3.list.DayInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val TAG = "TAG"
    val latitude = "52.52"
    val longitude = "13.41"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transparentSystemBars()
        requestCurrentTime()
        setUprDailyRecyclerView()
    }

    private fun setUprDailyRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter =
            DayAdapter(createMutableList()) { dayInfo -> changeScreen(dayInfo) }
    }

    private fun changeScreen(dayInfo: DayInfo) {
        val i = Intent(this, DetailDay::class.java).apply {
            putExtra("dayInfo", dayInfo.sunrise)
        }
        startActivity(i)
    }

    fun createMutableList(): MutableList<DayInfo> {
        var dailyInfo: MutableList<DayInfo> = ArrayList()
        dailyInfo.add(DayInfo("1", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("2", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("3", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("4", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("5", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("6", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("7", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("8", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("9", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("10", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("11", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("12", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("13", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("14", "sunset", "temperatureMax", "temperatureMin", "time"))
        dailyInfo.add(DayInfo("15", "sunset", "temperatureMax", "temperatureMin", "time"))
        return dailyInfo
    }

    fun requestDailyInfo() {

    }

    private fun transparentSystemBars() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun requestCurrentTime() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(MeteoAPIService::class.java)

        val latitud = "20.0"
        val longitud = "12.0"
        val currentParams = "temperature_2m,is_day,rain,showers,snowfall"
        val daily = "temperature_2m_max,temperature_2m_min,sunrise,sunset"
        val forecastDays = "1"
        apiService.getWeather(latitud, longitud, currentParams, daily, forecastDays).enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "onResponse: ${response.raw()}")
                    Log.w(TAG, "onResponse: DATOS =>  ${response.body()}")
                    Log.w(TAG, "onResponse: DATOS ACTUALES =>  ${response.body()?.current}")
                    val currentWeather: CurrentWeather? = response.body()?.current
                    Log.i(TAG, "onResponse: OBJETO => ${currentWeather}")
                    binding.textView2.text = currentWeather?.temperature.toString()
                    binding.textView3.text = currentWeather?.rain.toString()
                    val todayWeather: Daily? = response.body()?.daily
                    Log.i(TAG, "onResponse: OBJETO => ${todayWeather}")
                    binding.textView4.text = "Min.: ${todayWeather?.temperatureMin?.get(0).toString()}"
                    binding.textView5.text = "Max.: ${todayWeather?.temperatureMax?.get(0).toString()}"
                    binding.textView6.text = todayWeather?.sunrise?.get(0)
                    binding.textView7.text = todayWeather?.sunset?.get(0)

                } else {

                    Log.w(TAG, "Error en la solicitud")
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, throwable: Throwable) {
                Log.w(TAG, "onFailure: ERROR => ${throwable.message}")
            }
        })
    }
}
