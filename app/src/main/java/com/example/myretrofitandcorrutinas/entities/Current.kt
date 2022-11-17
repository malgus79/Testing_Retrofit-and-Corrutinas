package com.example.myretrofitandcorrutinas.entities

data class Current(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val humidity: Int,
    val weather: List<Weather>)
