package com.qualentum.sprint3.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualentum.sprint3.detail.data.mappers.DetailWeather
import com.qualentum.sprint3.detail.domain.usecases.GetDetailWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getDetailWeatherUseCase: GetDetailWeatherUseCase,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val detailDayMutableState: MutableStateFlow<DetailWeather?> =
        MutableStateFlow(DetailWeather(ArrayList(emptyList())))
    val detailDayState: StateFlow<DetailWeather?> = detailDayMutableState

    private val loadingMutableState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> = loadingMutableState

    private val errorMutableState = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = errorMutableState

    init {
        val day = savedStateHandle.get<String>("xString")
        viewModelScope.launch(Dispatchers.IO) {
            loadingMutableState.value = true
            getDetailWeatherUseCase.invoke(day).onSuccess {
                detailDayMutableState.value = it
            }.onFailure {
                errorMutableState.value = true
            }
            loadingMutableState.value = false
        }
    }

    fun setSelectedDay(selectedDay: String) {
        savedStateHandle["selectedDay"] = selectedDay
    }
}