package com.qualentum.sprint3.main.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.qualentum.sprint3.R
import com.qualentum.sprint3.common.data.Constants
import com.qualentum.sprint3.common.ui.CommonError
import com.qualentum.sprint3.common.ui.GetWeatherState.Companion.getWeatherDescription
import com.qualentum.sprint3.databinding.ActivityMainBinding
import com.qualentum.sprint3.detail.ui.DetailDay
import com.qualentum.sprint3.main.data.mappers.OneDay
import com.qualentum.sprint3.main.ui.list.DayAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

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
                binding.tvTodayWeatherDescription.text = getWeatherDescription(this@MainActivity, it?.state)
                binding.tvTodaySunrise.text = it?.sunrise
                binding.tvTodaySunset.text = it?.sunset
                binding.tvTodayMaxTemperature.text = it?.temperatureMax.toString()
                binding.tvTodayMinTemperature.text = it?.temperatureMin.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.listsDayWeatherState.collect {
                    setUpDailyRecyclerView(it?.itemsList)
            }
        }

        lifecycleScope.launch {
            viewModel.errorState.collect {
                if (it) {
                    CommonError.show(this@MainActivity)
                }
            }
        }
    }

    private fun setUpDailyRecyclerView(itemList: MutableList<OneDay>?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter =
            DayAdapter(itemList) { oneDay -> changeScreen(oneDay) }
    }

    private fun changeScreen(dayInfo: OneDay?) {
        val i = Intent(this, DetailDay::class.java).apply {
            putExtra("dayInfo", dayInfo?.time)
            putExtra("latitude", Constants.LATITUDE.toString())
            putExtra("longitude", Constants.LONGITUDE.toString())
        }
        startActivity(i)
    }

    private fun showDayNightIcon(isDay: Int?): Drawable? {
        return if (isDay == 1){
            ContextCompat.getDrawable(this, R.drawable.day_svg)
        } else {
            ContextCompat.getDrawable(this, R.drawable.night_svg)
        }
    }
}
