package com.example.listedapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listedapplication.model.DashboardDataModel
import com.example.listedapplication.repo.DashboardRemoteApiRepository
import com.example.listedapplication.service.ApiResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repository: DashboardRemoteApiRepository
) : ViewModel() {

    private var _resLiveData = MutableSharedFlow<DashboardData>()
    val resLiveData = _resLiveData.asSharedFlow()

    private var _greeting = MutableSharedFlow<String>()
    val greeting = _greeting.asSharedFlow()

    private var _data = MutableSharedFlow<DashboardDataModel>()
    val data = _data.asSharedFlow()

    fun callDashboardRemotely() {
        viewModelScope.launch {
            _resLiveData.emit(DashInProgress)
            when(val res = repository.apiInvoked()) {
                ApiResponse.Error -> {
                    _resLiveData.emit(DashFailed)
                }
                is ApiResponse.Success -> {
                    _resLiveData.emit(DashSuccess)
                    sendDataForMagic(res.data)
                }
            }
        }
    }

    private fun sendDataForMagic(data: DashboardDataModel) {
        viewModelScope.launch {
            _data.emit(data)
        }
    }

    fun checkForGreeting() {
        viewModelScope.launch {
            val c: Calendar = Calendar.getInstance()
            when (c.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> {
                    _greeting.emit("Good Morning")
                }
                in 12..15 -> {
                    _greeting.emit("Good Afternoon")
                }
                else -> {
                    _greeting.emit("Good Evening")
                }
            }
        }
    }

    // API Response sealed status
    sealed class DashboardData
    object DashSuccess : DashboardData()
    object DashFailed : DashboardData()
    object DashInProgress : DashboardData()
}