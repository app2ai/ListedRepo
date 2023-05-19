package com.example.listedapplication.model

import com.google.gson.annotations.SerializedName

data class DashboardDataModel(
    val applied_campaign: Int,
    @SerializedName("data")
    val mData: Data,
    val extra_income: Double,
    val links_created_today: Int,
    val message: String,
    val startTime: String,
    val status: Boolean,
    val statusCode: Int,
    val support_whatsapp_number: String,
    val today_clicks: Int,
    val top_location: String,
    val top_source: String,
    val total_clicks: Int,
    val total_links: Int
)

data class Data(
    val overall_url_chart: Map<String, Int>,
    val recent_links: List<LinkDetails>,
    val top_links: List<LinkDetails>
)

data class LinkDetails(
    val app: String,
    val created_at: String,
    val domain_id: String,
    val original_image: String,
    val smart_link: String,
    val thumbnail: Any,
    val times_ago: String,
    val title: String,
    val total_clicks: Int,
    val url_id: Int,
    val url_prefix: Any,
    val url_suffix: String,
    val web_link: String
)