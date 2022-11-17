package com.example.myretrofitandcorrutinas.mainModule.viewModel

import com.example.myretrofitandcorrutinas.common.dataAccess.WeatherForecastService
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
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
    fun setup() {
        mainViewModel = MainViewModel()
        service = retrofit.create(WeatherForecastService::class.java)
    }

    //test: no tiene valor nulo
    @Test
    fun checkCurrentWeatherIsNotNullTest() {
        runBlocking {
            val result = service.getWeatherForecastByCoordinates(
                25.294741, 51.535293,
                "6a5c325c9265883997730d09be2328e8", "metric", "en"
            )
            assertThat(result.current, `is`(notNullValue()))
        }
    }

    //test: verificar los datos que vienen desde el servidor con retrofit y corrutinas
    @Test
    fun checkTimezoneReturnsQatarTest(){
        runBlocking {
            val result = service.getWeatherForecastByCoordinates(25.294741, 51.535293,
                "6a5c325c9265883997730d09be2328e8", "metric", "en")
            assertThat(result.timezone, `is`("Asia/Qatar"))
        }
    }

    //test: provocar error esperado
    @Test
    fun checkErrorResponseWithOnlyCoordinatesTes(){
        runBlocking {
            try {
                service.getWeatherForecastByCoordinates(25.294741, 51.535293,
                    "", "", "")
            } catch (e: Exception) {
                assertThat(e.localizedMessage, `is`("HTTP 401 Unauthorized"))
            }
        }
    }
}