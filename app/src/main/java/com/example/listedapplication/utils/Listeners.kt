package com.example.listedapplication.utils

import com.example.listedapplication.model.LinkDetails

interface FetchLinksFromRemoteListener{
    fun onLinksFetched(list: List<LinkDetails>)
}
interface FetchRecLinksFromRemoteListener{
    fun onRecLinksFetched(list: List<LinkDetails>)
}