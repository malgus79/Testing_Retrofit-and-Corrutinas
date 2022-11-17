package com.example.myretrofitandcorrutinas.mainModule.model

import com.example.myretrofitandcorrutinas.common.dataAccess.WeatherForecastService
import com.example.myretrofitandcorrutinas.entities.WeatherForecastEntity

class MainRepository(private val service: WeatherForecastService) {
    suspend fun getWeatherAndForecast(lat: Double, lon: Double, appId: String, units: String,
                                      lang: String) : WeatherForecastEntity {
        return service.getWeatherForecastByCoordinates(lat, lon, appId, units, lang)
    }
}