package com.qualentum.sprint3.main.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.qualentum.sprint3.R
import com.qualentum.sprint3.common.data.model.Coordinates
import com.qualentum.sprint3.databinding.ActivityMainBinding
import com.qualentum.sprint3.detail.ui.DetailDay
import com.qualentum.sprint3.main.data.model.nextdays.DailyLists
import com.qualentum.sprint3.main.data.model.nextdays.OneDay
import com.qualentum.sprint3.main.data.model.today.CurrentDay
import com.qualentum.sprint3.main.ui.list.DayAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding
    val latitude = 40.41
    val longitude = -3.70
    val coordinates = Coordinates.Madrid
    private val forecastDaysConst = 7

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
            viewModel.currentWeatherState.collect {
                binding.tvCurrentTemperature.text = it?.temperature.toString()
                binding.tvTodayWeatherIcon.text = it?.rain.toString()
            }
        }
        lifecycleScope.launch {
            viewModel.dayWeatherState.collect {
                if (checkDayWeatherLists(it)) {
                    binding.tvTodayMinTemperature.text = it?.temperatureMin?.get(0).toString()
                    binding.tvTodayMinTemperature.text = it?.temperatureMax?.get(0).toString()
                    binding.tvTodaySunrise.text = it?.sunrise?.get(0).toString()
                    binding.tvTodaySunset.text = it?.sunset?.get(0).toString()
                }

            }
        }

        lifecycleScope.launch {
            viewModel.listsDayWeatherState.collect {
                if (checkDayWeatherLists( it )) setUprDailyRecyclerView( it )
            }
        }
    }

    private fun setUprDailyRecyclerView(daily: DailyLists?) {
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


}
