package com.example.listedapplication.repo

import com.example.listedapplication.model.DashboardDataModel
import com.example.listedapplication.service.ApiResponse
import com.example.listedapplication.service.ApiService
import com.example.listedapplication.utils.Constants.ACCESS_TOKEN
import com.example.listedapplication.utils.Constants.PROXY_V1
import javax.inject.Inject

class DashboardRemoteApiRepository @Inject constructor(
    private val service: ApiService
) {
    suspend fun apiInvoked() : ApiResponse{
        return try {
            service.dashboardApi(
                PROXY_V1
            ).body()?.let {
                ApiResponse.Success(it)
            } ?: ApiResponse.Error
        } catch (ex: Exception) {
            ApiResponse.Error
        }
    }
}