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
import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.common.ui.CommonError
import com.qualentum.sprint3.databinding.ActivityDetailDayBinding
import com.qualentum.sprint3.detail.data.mappers.DetailWeather
import com.qualentum.sprint3.detail.data.repository.remote.DetailRepository
import com.qualentum.sprint3.detail.domain.usecases.GetDetailWeatherUseCase
import com.qualentum.sprint3.detail.ui.list.DetailAdapter
import kotlinx.coroutines.launch

class DetailDay : AppCompatActivity() {
    private var viewModel: DetailViewModel? = null
    private var detailRepository: DetailRepository? = null
    private var getDetailWeatherUseCase: GetDetailWeatherUseCase? = null
    private lateinit var binding: ActivityDetailDayBinding
    private lateinit var day: String
    private var latitude: Double = 0.0
    private var longitude : Double = 0.0
    private val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBundle()
        setUpRepository()
        setUpDetailUseCase()
        setUpViewModel()
        binding.tvDay.text = day
        transparentSystemBars()
        observeViewModel()
    }

    private fun setUpViewModel() {
        viewModel = DetailViewModel(getDetailWeatherUseCase!!)
    }

    private fun setUpDetailUseCase() {
        getDetailWeatherUseCase = GetDetailWeatherUseCase(
            detailRepository!!
        )
    }

    private fun setUpRepository() {
        detailRepository = DetailRepository(
            OpenMeteoClient.detailService,
            day,
            latitude,
            longitude
        )
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


    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel?.detailDayState?.collect {
                //if (checkDayWeatherLists(it)) {
                    setUpGrid(it)
                //}
            }
        }
        lifecycleScope.launch {
            viewModel?.loadingState?.collect { visibility ->
                binding.progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
            }
        }
        lifecycleScope.launch {
            viewModel?.errorState?.collect {
                if (it) {
                    CommonError.show(this@DetailDay)
                }
            }
        }
    }

    private fun setUpGrid(detailWeather: DetailWeather?){
        binding.recyclerViewDetail.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewDetail.adapter = DetailAdapter(detailWeather)
    }
}

