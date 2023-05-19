package com.example.listedapplication.utils

fun Map<String, Int>.greatestValue() : Int {
    var top = 0
    for (value in this.values) {
        if (value>top) top = value
    }
    return top+2
}