package com.qualentum.sprint3.detail.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.qualentum.sprint3.R
import com.qualentum.sprint3.common.data.DEGREE_SYMBOL
import com.qualentum.sprint3.databinding.ActivityDetailDayBinding
import com.qualentum.sprint3.detail.data.model.CardData
import com.qualentum.sprint3.detail.data.model.day.DayDetailLists
import com.qualentum.sprint3.detail.ui.list.DetailAdapter
import kotlinx.coroutines.launch

class DetailDay : AppCompatActivity() {
    var viewModel: DetailViewModel? = null
    private lateinit var binding: ActivityDetailDayBinding
    lateinit var day: String
    var latitude: Double = 0.0
    var longitude : Double = 0.0
    val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBundle()
        viewModel = DetailViewModel(day, latitude, longitude)
        binding.tvDay.text = day
        transparentSystemBars()
        setUpDetailViewModel()
    }

    private fun getBundle() {
        val bundle = intent.extras
        day = bundle?.getString("dayInfo").toString()
        latitude = bundle?.getString("latitude").toString().toDouble()
        longitude = bundle?.getString("longitude").toString().toDouble()
    }

    private fun transparentSystemBars() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun setUpDetailViewModel() {
        lifecycleScope.launch {
            viewModel?.detailDayState?.collect {
                if (checkDayWeatherLists(it)) {
                    setUpGrid(it)
                }
            }
        }
        lifecycleScope.launch {
            viewModel?.loadingState?.collect { visibility ->
                binding.progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
            }
        }
    }


    private fun checkDayWeatherLists(dayDetailLists: DayDetailLists?): Boolean {
        if (dayDetailLists?.apparentTemperatureMax?.isEmpty() == true) return false
        if (dayDetailLists?.apparentTemperatureMin?.isEmpty() == true) return false
        if (dayDetailLists?.precipitationHours?.isEmpty() == true) return false
        if (dayDetailLists?.rainSum?.isEmpty() == true) return false
        if (dayDetailLists?.showersSum?.isEmpty() == true) return false
        if (dayDetailLists?.snowfallSum?.isEmpty() == true) return false
        if (dayDetailLists?.sunrise?.isEmpty() == true) return false
        if (dayDetailLists?.sunset?.isEmpty() == true) return false
        if (dayDetailLists?.temperatureMax?.isEmpty() == true) return false
        if (dayDetailLists?.temperatureMin?.isEmpty() == true) return false
        if (dayDetailLists?.time?.isEmpty() == true) return false
        if (dayDetailLists?.uv_index_max?.isEmpty() == true) return false
        return true
    }

    private fun setUpGrid(dayInfo: DayDetailLists?){
        val itemsList: MutableList<CardData> = ArrayList()
        itemsList.add(CardData("Min. Temp.", dayInfo?.temperatureMin?.get(0).toString() + DEGREE_SYMBOL, this.getDrawable(R.drawable.thermometer_cold_svg)))
        itemsList.add(CardData("Max. Temp.", dayInfo?.temperatureMax?.get(0).toString() + DEGREE_SYMBOL, this.getDrawable(R.drawable.thermometer_hot_svg)))
        itemsList.add(CardData("Sensación Min.", dayInfo?.apparentTemperatureMin?.get(0).toString() + DEGREE_SYMBOL, this.getDrawable(R.drawable.thermometer_cold_svg)))
        itemsList.add(CardData("Sensación Max.", dayInfo?.apparentTemperatureMax?.get(0).toString() + DEGREE_SYMBOL, this.getDrawable(R.drawable.thermometer_hot_svg)))
        itemsList.add(CardData("Amanecer", dayInfo?.sunrise?.get(0).toString(), this.getDrawable(R.drawable.sunrise_svg)))
        itemsList.add(CardData("Anochecer", dayInfo?.sunset?.get(0).toString(), this.getDrawable(R.drawable.sunset_svg)))
        itemsList.add(CardData("UV Max.", dayInfo?.uv_index_max?.get(0).toString(), this.getDrawable(R.drawable.uv_ray_svg)))
        itemsList.add(CardData("Prob. lluvia", dayInfo?.rainSum?.get(0).toString(), this.getDrawable(R.drawable.umbrella_svg)))
        itemsList.add(CardData("Prob. chubascos", dayInfo?.showersSum?.get(0).toString(), this.getDrawable(R.drawable.umbrella_2_svg)))
        itemsList.add(CardData("Prob. nevadas", dayInfo?.snowfallSum?.get(0).toString(), this.getDrawable(R.drawable.snowflake_svg)))
        binding.recyclerViewDetail.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewDetail.adapter = DetailAdapter(itemsList)
    }
}

