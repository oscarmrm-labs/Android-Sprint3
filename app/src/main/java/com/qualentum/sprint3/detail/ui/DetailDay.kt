package com.qualentum.sprint3.detail.ui

import android.os.Bundle
import android.util.Log
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
        binding.tvDay.text = "Prueba"
        getBundle()
        viewModel = DetailViewModel(day, latitude, longitude)
        binding.tvDay.text = day
        setUpActivity()
        transparentSystemBars()
        setUpDetailViewModel()
    }

    private fun getBundle() {
        val bundle = intent.extras
        Log.i(TAG, "getBundle: " + bundle)
        Log.i(TAG, "getBundle: " + bundle?.getString("dayInfo"))
        Log.i(TAG, "getBundle: " + bundle?.getString("latitude"))
        Log.i(TAG, "getBundle: " + bundle?.getString("longitude"))
        day = bundle?.getString("dayInfo").toString()
        latitude = bundle?.getString("latitude").toString().toDouble()
        longitude = bundle?.getString("longitude").toString().toDouble()
    }

    private fun setUpActivity(){
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
        val cardAdapter = GridRVAdapter(itemsList, this)
        binding.gridLayout.adapter = cardAdapter
    }
}

