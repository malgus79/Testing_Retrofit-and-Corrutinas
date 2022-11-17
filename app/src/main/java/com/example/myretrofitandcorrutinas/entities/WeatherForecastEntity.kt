package com.example.myretrofitandcorrutinas.entities

//principal
data class WeatherForecastEntity(val timezone: String,
                                 val current: Current,  //clima actual
                                 val hourly: List<Forecast>)