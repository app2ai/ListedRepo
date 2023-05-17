package com.example.listedapplication.service

import com.example.listedapplication.model.DashboardDataModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/api/v1/dashboardNew/")
    suspend fun dashboardApi(): Response<DashboardDataModel>
}
