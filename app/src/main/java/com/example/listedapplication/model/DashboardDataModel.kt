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
    val overall_url_chart: OverallUrlChart,
    val recent_links: List<LinkDetails>,
    val top_links: List<LinkDetails>
)

data class OverallUrlChart(
    val `2023-04-17`: Int,
    val `2023-04-18`: Int,
    val `2023-04-19`: Int,
    val `2023-04-20`: Int,
    val `2023-04-21`: Int,
    val `2023-04-22`: Int,
    val `2023-04-23`: Int,
    val `2023-04-24`: Int,
    val `2023-04-25`: Int,
    val `2023-04-26`: Int,
    val `2023-04-27`: Int,
    val `2023-04-28`: Int,
    val `2023-04-29`: Int,
    val `2023-04-30`: Int,
    val `2023-05-01`: Int,
    val `2023-05-02`: Int,
    val `2023-05-03`: Int,
    val `2023-05-04`: Int,
    val `2023-05-05`: Int,
    val `2023-05-06`: Int,
    val `2023-05-07`: Int,
    val `2023-05-08`: Int,
    val `2023-05-09`: Int,
    val `2023-05-10`: Int,
    val `2023-05-11`: Int,
    val `2023-05-12`: Int,
    val `2023-05-13`: Int,
    val `2023-05-14`: Int,
    val `2023-05-15`: Int,
    val `2023-05-16`: Int,
    val `2023-05-17`: Int,
    val `2023-05-18`: Int
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