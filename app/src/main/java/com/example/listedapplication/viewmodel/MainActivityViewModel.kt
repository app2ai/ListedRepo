package com.example.listedapplication.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listedapplication.model.DashboardDataModel
import com.example.listedapplication.model.LinkDetails
import com.example.listedapplication.repo.DashboardRemoteApiRepository
import com.example.listedapplication.service.ApiResponse
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repository: DashboardRemoteApiRepository
) : ViewModel() {

    private var _resLiveData = MutableLiveData<DashboardData>()
    val resLiveData: LiveData<DashboardData> = _resLiveData

    private var _greeting = MutableLiveData<String>()
    val greeting: LiveData<String> = _greeting

    private var _data = MutableLiveData<DashboardDataModel>()
    val data: LiveData<DashboardDataModel> = _data

    fun callDashboardRemotely() {
        viewModelScope.launch {
            _resLiveData.value = DashInProgress
            when(val res = repository.apiInvoked()) {
                ApiResponse.Error -> {
                    _resLiveData.value = DashFailed
                }
                is ApiResponse.Success -> {
                    _resLiveData.value = DashSuccess
                    sendDataForMagic(res.data)
                }
            }
        }
    }

    private fun sendDataForMagic(data: DashboardDataModel) {
        _data.value = data
    }

    fun checkForGreeting() {
        val c: Calendar = Calendar.getInstance()
        when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> {
                _greeting.value = "Good Morning"
            }
            in 12..15 -> {
                _greeting.value = "Good Afternoon"
            }
            else -> {
                _greeting.value = "Good Evening"
            }
        }
    }

    // API Response sealed status
    sealed class DashboardData
    object DashSuccess : DashboardData()
    object DashFailed : DashboardData()
    object DashInProgress : DashboardData()
}