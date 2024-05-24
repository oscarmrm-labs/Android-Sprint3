package com.qualentum.sprint3.main.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.qualentum.sprint3.R
import com.qualentum.sprint3.common.ui.GetWeatherState.getWeatherDescription
import com.qualentum.sprint3.common.ui.GetWeatherState.getWeatherIcon
import com.qualentum.sprint3.databinding.ActivityMainBinding
import com.qualentum.sprint3.detail.ui.DetailDay
import com.qualentum.sprint3.main.data.model.nextdays.DailyLists
import com.qualentum.sprint3.main.data.model.nextdays.OneDay
import com.qualentum.sprint3.main.data.model.today.CurrentDay
import com.qualentum.sprint3.main.ui.list.DayAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val latitude = 64.41
    val longitude = -8.70
    private val forecastDaysConst = 7
    private val viewModel = MainViewModel(latitude, longitude)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transparentSystemBars()
        setUpMainViewModel()

    }

    private fun transparentSystemBars() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpMainViewModel() {
        lifecycleScope.launch {
            viewModel.loadingState.collect { visibility ->
                binding.progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
            }
        }
        lifecycleScope.launch {
            viewModel.currentWeatherState.collect {
                binding.tvCurrentTemperature.text = it?.temperature.toString()

                binding.iconDayNight.setImageDrawable(showDayNightIcon(it?.isDay))
            }
        }
        lifecycleScope.launch {
            viewModel.dayWeatherState.collect {
                if (checkDayWeatherLists(it)) {
                    binding.tvTodayMinTemperature.text = it?.temperatureMin?.get(0).toString()
                    binding.tvTodayMaxTemperature.text = it?.temperatureMax?.get(0).toString()
                    binding.tvTodaySunrise.text = it?.sunrise?.get(0).toString()
                    binding.tvTodaySunset.text = it?.sunset?.get(0).toString()
                }

            }
        }

        lifecycleScope.launch {
            viewModel.listsDayWeatherState.collect {
                if (checkDayWeatherLists( it )) setUpDailyRecyclerView( it )
            }
        }

        lifecycleScope.launch {
            viewModel.weatherStateState.collect {
                binding.tvTodayWeatherDescription.text = getWeatherDescription(binding.root.context, it)
                binding.iconStateWeather.setImageDrawable(getWeatherIcon(binding.root.context, it))
            }
        }
    }

    private fun setUpDailyRecyclerView(daily: DailyLists?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter =
            DayAdapter(inflateDaily(daily)) { oneDay -> changeScreen(oneDay) }
    }

    private fun inflateDaily(daily: DailyLists?): MutableList<OneDay> {
        val dailyInfo: MutableList<OneDay> = ArrayList()
        for (i in 0..<forecastDaysConst) {
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
            putExtra("latitude", latitude.toString())
            putExtra("longitude", longitude.toString())
        }
        startActivity(i)
    }

    private fun checkDayWeatherLists(currentDay: CurrentDay?): Boolean {
        if (currentDay?.temperatureMin?.isEmpty() == true) return false
        if (currentDay?.temperatureMax?.isEmpty() == true) return false
        if (currentDay?.sunrise?.isEmpty() == true) return false
        if (currentDay?.sunset?.isEmpty() == true) return false
        return true
    }

    private fun checkDayWeatherLists(daily: DailyLists?): Boolean {
        if (daily?.temperatureMin?.isEmpty() == true) return false
        if (daily?.temperatureMax?.isEmpty() == true) return false
        if (daily?.rainSum?.isEmpty() == true) return false
        if (daily?.showersSum?.isEmpty() == true) return false
        if (daily?.snowfallSum?.isEmpty() == true) return false
        return true
    }

    private fun showDayNightIcon(isDay: Int?): Drawable? {
        return if (isDay == 1){
            ContextCompat.getDrawable(this, R.drawable.day_svg)
        } else {
            ContextCompat.getDrawable(this, R.drawable.night_svg)
        }
    }
/*
    private fun showWeatherIcon(currentWeather: String): Drawable? {
        return when (currentWeather) {
            "rain" -> ContextCompat.getDrawable(this, R.drawable.rain_svg)
            "showers" -> ContextCompat.getDrawable(this, R.drawable.showers_svg)
            "snowfall" -> ContextCompat.getDrawable(this, R.drawable.snowfall_svg)
            "cloudly" -> ContextCompat.getDrawable(this, R.drawable.cloudy_day_1_svg)
            "cloudly2" -> ContextCompat.getDrawable(this, R.drawable.cloudy_day_2_svg)
            "cloudly3" -> ContextCompat.getDrawable(this, R.drawable.cloudy_day_3_svg)
            else -> ContextCompat.getDrawable(this, R.drawable.day_svg)
        }
    }
 */
}
