package com.example.myretrofitandcorrutinas.entities

//datos complementarios
data class Forecast(val dt: Long,
                    val temp: Double,
                    val humidity: Int,
                    val weather: List<Weather>,
                    val pop: Double)
