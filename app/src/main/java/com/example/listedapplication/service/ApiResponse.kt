package com.example.listedapplication.service

import com.example.listedapplication.model.DashboardDataModel

/*
Collect API response dynamically for all type of request
T is generic class for response
*/
sealed class ApiResponse {
    data class Success(val data: DashboardDataModel) : ApiResponse()
    object Error: ApiResponse()
}
