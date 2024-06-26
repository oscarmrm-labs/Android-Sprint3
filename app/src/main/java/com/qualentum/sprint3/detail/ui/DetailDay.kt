package com.qualentum.sprint3.detail.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.qualentum.sprint3.R
import com.qualentum.sprint3.common.ui.CommonError
import com.qualentum.sprint3.databinding.ActivityDetailDayBinding
import com.qualentum.sprint3.detail.data.mappers.DetailWeather
import com.qualentum.sprint3.detail.ui.list.DetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailDay @Inject constructor() : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailDayBinding
    private lateinit var day: String
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBundle()
        viewModel.setSelectedDay(day)
        //binding.tvDay.text = day
        transparentSystemBars()
        observeViewModel()
    }

    private fun getBundle() {
        val bundle = intent.extras
        day = bundle?.getString("dayInfo").toString()
        latitude = bundle?.getString("latitude").toString().toDouble()
        longitude = bundle?.getString("longitude").toString().toDouble()
        viewModel.savedStateHandle["selectedDay"] = day
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
            viewModel.detailDayState.collect {
                setUpGrid(it)
            }
        }
        lifecycleScope.launch {
            viewModel.loadingState.collect { visibility ->
                binding.progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
            }
        }
        lifecycleScope.launch {
            viewModel.errorState.collect {
                if (it) {
                    CommonError.show(this@DetailDay)
                }
            }
        }
    }

    private fun setUpGrid(detailWeather: DetailWeather?) {
        binding.recyclerViewDetail.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewDetail.adapter = DetailAdapter(detailWeather)
    }
}

