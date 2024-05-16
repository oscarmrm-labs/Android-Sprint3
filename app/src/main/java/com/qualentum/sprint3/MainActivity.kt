package com.qualentum.sprint3

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.qualentum.sprint3.databinding.ActivityMainBinding
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

        val route = "forecast?" +
                "latitude=$latitude" +
                "&longitude=$longitude" +
                "&current=" +
                    "temperature_2m," +
                    "is_day," +
                    "rain," +
                    "showers," +
                    "snowfall"
        Log.i(TAG, "requestCurrentTime: Ruta => " + retrofit.baseUrl() + route)

        val latitud = "20.0"
        val longitud = "12.0"
        val currentParams = "temperature_2m,is_day,rain,showers,snowfall"
        apiService.getWeather(latitud, longitud, currentParams).enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "onResponse: ${response.raw()}")
                    Log.w(TAG, "onResponse: DATOS =>  ${response.body()}")
                    Log.w(TAG, "onResponse: DATOS ACTUALES =>  ${response.body()?.current}")
                    val currentWeather: CurrentWeather? = response.body()?.current
                    Log.i(TAG, "onResponse: OBJETO => ${currentWeather}")
                    binding.textView2.text = currentWeather?.temperature2m.toString()
                    binding.textView3.text = currentWeather?.rain.toString()

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
