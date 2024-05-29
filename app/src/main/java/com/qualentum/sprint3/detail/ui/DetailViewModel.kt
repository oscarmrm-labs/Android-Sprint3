package com.qualentum.sprint3.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qualentum.sprint3.detail.data.model.day.DayDetailLists
import com.qualentum.sprint3.detail.data.repository.remote.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(repository: DetailRepository): ViewModel() {

    private val detailDayMutableState: MutableStateFlow<DayDetailLists?> = MutableStateFlow(DayDetailLists(emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()))
    val detailDayState: StateFlow<DayDetailLists?> = detailDayMutableState

    private val loadingMutableState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> = loadingMutableState

    private val errorMutableState = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = errorMutableState

    init {
        viewModelScope.launch(Dispatchers.IO){
            loadingMutableState.value = true
            repository.fetchDetailData2().onSuccess {
                val result = it.dayDetailLists
                detailDayMutableState.value = result
            }.onFailure {
                errorMutableState.value = true
            }
            loadingMutableState.value = false
        }
    }


}