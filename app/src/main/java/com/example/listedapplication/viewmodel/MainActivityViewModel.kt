package com.example.listedapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listedapplication.model.DashboardDataModel
import com.example.listedapplication.repo.DashboardRemoteApiRepository
import com.example.listedapplication.service.ApiResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repository: DashboardRemoteApiRepository
) : ViewModel() {

    private var _resLiveData = MutableLiveData<DashboardData>()
    val resLiveData: LiveData<DashboardData> = _resLiveData

    fun callDashboardRemotely() {
        viewModelScope.launch {
            _resLiveData.value = DashInProgress
            when(val res = repository.apiInvoked()) {
                ApiResponse.Error -> {
                    _resLiveData.value = DashFailed
                }
                is ApiResponse.Success -> {
                    _resLiveData.value = DashSuccess(res.data)
                }
            }
        }
    }

    // API Response sealed status
    sealed class DashboardData
    data class DashSuccess(val dashData: DashboardDataModel) : DashboardData()
    object DashFailed : DashboardData()
    object DashInProgress : DashboardData()
}