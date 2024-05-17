package com.qualentum.sprint3.detail.ui

import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.qualentum.sprint3.R
import com.qualentum.sprint3.detail.data.model.CardData
import com.qualentum.sprint3.detail.data.model.day.DayDetailLists
import com.qualentum.sprint3.detail.data.model.day.DayDetailResponse
import com.qualentum.sprint3.main.data.repository.MeteoAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailDay : AppCompatActivity() {
    val TAG: String = "TAG"
    val latitude = 40.41
    val longitude = -3.70
    lateinit var prueba: TextView
    lateinit var gridLayout: GridView
    lateinit var itemsList: MutableList<CardData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_day)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showIndex()
        requestDayDetail()
    }

    private fun showIndex() {
        val b = intent.getStringExtra("dayInfo")
        // TODO: recoger coordenadas para hacer la consulta con los mismos datos
        prueba = findViewById<TextView>(R.id.textView12)
        prueba.text = b
        gridLayout = findViewById(R.id.gridLayout)
    }
    private fun requestDayDetail() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(MeteoAPIService::class.java)

        val daily = "temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset," +
                "uv_index_max," +
                ",," + // FIXME: El campo " rain_sum " da error en la solicitud
                "showers_sum,snowfall_sum"
        //val daily = "temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,
        // uv_index_max,rain_sum,showers_sum,snowfall_sum,precipitation_hours"
        val startDay = prueba.text.toString()
        val endDay = prueba.text.toString()
        apiService.getDayDetail(latitude, longitude, daily, startDay, endDay).enqueue(object :
            Callback<DayDetailResponse> {
            override fun onResponse(call: Call<DayDetailResponse>, response: Response<DayDetailResponse>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "onResponse: ${response.raw()}")
                    Log.w(TAG, "onResponse: DATOS =>  ${response.body()}")
                    Log.w(TAG, "onResponse: DATOS ACTUALES =>  ${response.body()?.daily}")
                    val dayInfo: DayDetailLists? = response.body()?.daily
                    setUpGrid(dayInfo)
                } else {

                    Log.w(TAG, "Error en la solicitud")
                }
            }

            override fun onFailure(call: Call<DayDetailResponse>, throwable: Throwable) {
                Log.w(TAG, "onFailure: ERROR => ${throwable.message}")
            }
        })
    }
    fun setUpGrid(dayInfo: DayDetailLists?){
        var itemsList: MutableList<CardData> = ArrayList()
        itemsList.add(CardData("Max. Temp.", dayInfo?.temperature_2m_max?.get(0).toString()))
        itemsList.add(CardData("Min. Temp.", dayInfo?.temperature_2m_min?.get(0).toString()))
        itemsList.add(CardData("Sensación Max.", dayInfo?.apparent_temperature_max?.get(0).toString()))
        itemsList.add(CardData("Sensación Min.", dayInfo?.apparent_temperature_min?.get(0).toString()))
        itemsList.add(CardData("Amanecer", dayInfo?.sunrise?.get(0).toString()))
        itemsList.add(CardData("Anochecer", dayInfo?.sunset?.get(0).toString()))
        itemsList.add(CardData("UV Max.", dayInfo?.uv_index_max?.get(0).toString()))
        itemsList.add(CardData("Prob. lluvia", dayInfo?.rain_sum?.get(0).toString()))
        itemsList.add(CardData("Prob. chubascos", dayInfo?.showers_sum?.get(0).toString()))
        itemsList.add(CardData("Prob. nevadas", dayInfo?.snowfall_sum?.get(0).toString()))
        //itemsList.add(CardData("Prep horas", ""))
        val cardAdapter = GridRVAdapter(itemsList, this)
        gridLayout.adapter = cardAdapter
    }
}

