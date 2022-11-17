package com.example.myretrofitandcorrutinas.mainModule.viewModel

import com.example.myretrofitandcorrutinas.common.dataAccess.WeatherForecastService
import org.junit.Before
import org.junit.BeforeClass
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var service: WeatherForecastService

    companion object{
        private lateinit var retrofit: Retrofit

        @BeforeClass
        @JvmStatic
        fun setupCommon(){
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Before
    fun setup(){
        mainViewModel = MainViewModel()
        service = retrofit.create(WeatherForecastService::class.java)
    }
}