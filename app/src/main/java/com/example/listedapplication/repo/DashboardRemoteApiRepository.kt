package com.example.listedapplication.repo

import com.example.listedapplication.service.ApiResponse
import com.example.listedapplication.service.ApiService
import javax.inject.Inject

class DashboardRemoteApiRepository @Inject constructor(
    private val service: ApiService
) {
    suspend fun apiInvoked() : ApiResponse{
        return try {
            service.dashboardApi(
                proxyPath()
            ).body()?.let {
                ApiResponse.Success(it)
            } ?: ApiResponse.Error
        } catch (ex: Exception) {
            ApiResponse.Error
        }
    }

    companion object{
        external fun proxyPath(): String
    }
}