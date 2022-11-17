package com.example.myretrofitandcorrutinas.entities

//clima actual
data class Current(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val humidity: Int,
    val weather: List<Weather>)  //clima
