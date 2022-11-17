package com.example.myretrofitandcorrutinas.common.dataAccess


import com.example.myretrofitandcorrutinas.entities.WeatherForecastEntity
import retrofit2.http.GET
import retrofit2.http.Query

//servicio retrofit
interface WeatherForecastService {
    @GET("data/2.5/onecall")
    suspend fun getWeatherForecastByCoordinates(
        //parametros
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String,
        @Query("lang") lang: String): WeatherForecastEntity
}