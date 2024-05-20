package com.qualentum.sprint3.detail.ui

import android.os.Bundle
import android.widget.GridView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.qualentum.sprint3.R
import com.qualentum.sprint3.databinding.ActivityDetailDayBinding
import com.qualentum.sprint3.detail.data.model.CardData
import com.qualentum.sprint3.detail.data.model.day.DayDetailLists
import kotlinx.coroutines.launch

class DetailDay : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailDayBinding
    private lateinit var day: String
    var latitude: Double = 0.0
    var longitude : Double = 0.0
    private lateinit var gridLayout: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundle()
        viewModel = DetailViewModel(latitude, longitude)
        binding.tvDay.text = day
        transparentSystemBars()
        setUpDetailViewModel()
    }

    private fun transparentSystemBars() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_day)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getBundle() {
        day = intent.getStringExtra("dayInfo").toString()
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)
    }

    private fun setUpDetailViewModel() {
        lifecycleScope.launch {
            viewModel.detailDayState.collect {
                if (checkDayWeatherLists(it)) {
                    setUpGrid(it)
                }
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
        itemsList.add(CardData("Max. Temp.", dayInfo?.temperatureMax?.get(0).toString()))
        itemsList.add(CardData("Min. Temp.", dayInfo?.temperatureMin?.get(0).toString()))
        itemsList.add(CardData("Sensación Max.", dayInfo?.apparentTemperatureMax?.get(0).toString()))
        itemsList.add(CardData("Sensación Min.", dayInfo?.apparentTemperatureMin?.get(0).toString()))
        itemsList.add(CardData("Amanecer", dayInfo?.sunrise?.get(0).toString()))
        itemsList.add(CardData("Anochecer", dayInfo?.sunset?.get(0).toString()))
        itemsList.add(CardData("UV Max.", dayInfo?.uv_index_max?.get(0).toString()))
        itemsList.add(CardData("Prob. lluvia", dayInfo?.rainSum?.get(0).toString()))
        itemsList.add(CardData("Prob. chubascos", dayInfo?.showersSum?.get(0).toString()))
        itemsList.add(CardData("Prob. nevadas", dayInfo?.snowfallSum?.get(0).toString()))
        //itemsList.add(CardData("Prep horas", ""))
        val cardAdapter = GridRVAdapter(itemsList, this)
        gridLayout.adapter = cardAdapter
    }
}

