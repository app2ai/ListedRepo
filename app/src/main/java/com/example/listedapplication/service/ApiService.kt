package com.example.listedapplication.service

import com.example.listedapplication.model.DashboardDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("{proxy}/dashboardNew")
    suspend fun dashboardApi(
        @Path("proxy", encoded = true) proxy: String,
        @Query("token", encoded = true) token: String
    ): Response<DashboardDataModel>
}
