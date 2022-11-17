package com.example.myretrofitandcorrutinas.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myretrofitandcorrutinas.common.dataAccess.WeatherForecastService
import com.example.myretrofitandcorrutinas.entities.WeatherForecastEntity
import com.example.myretrofitandcorrutinas.mainModule.model.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    private val result = MutableLiveData<WeatherForecastEntity>()

    fun getResult(): LiveData<WeatherForecastEntity> = result

    private val repository: MainRepository by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherForecastService::class.java)
        MainRepository(service)
    }

    suspend fun getWeatherAndForecast(lat: Double, lon: Double, appId: String, units: String, lang: String){
        viewModelScope.launch {
            val resultServer = repository.getWeatherAndForecast(lat, lon, appId, units, lang)
            result.value = resultServer
        }
    }
}