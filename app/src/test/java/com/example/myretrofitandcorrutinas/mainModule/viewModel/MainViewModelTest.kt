package com.example.myretrofitandcorrutinas.mainModule.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myretrofitandcorrutinas.MainCoroutineRule
import com.example.myretrofitandcorrutinas.common.dataAccess.WeatherForecastService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModelTest {

    //regla para usar LiveData
    @get:Rule
    val instantExcecutorRule = InstantTaskExecutorRule()

    //Corrutinas
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutinesRule = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var service: WeatherForecastService

    companion object {
        private lateinit var retrofit: Retrofit

        @BeforeClass
        @JvmStatic
        fun setupCommon() {
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
                "09ed2cfc331705f6fd102606ed441669", "metric", "en"
            )
            assertThat(result.current, `is`(notNullValue()))
        }
    }

    //test: verificar los datos que vienen desde el servidor con retrofit y corrutinas
    @Test
    fun checkTimezoneReturnsQatarTest(){
        runBlocking {
            val result = service.getWeatherForecastByCoordinates(
                25.294741, 51.535293,
                "09ed2cfc331705f6fd102606ed441669", "metric", "en"
            )
            assertThat(result.timezone, `is`("Asia/Qatar"))
        }
    }

    //test: provocar error esperado
    @Test
    fun checkErrorResponseWithOnlyCoordinatesTes() {
        runBlocking {
            try {
                service.getWeatherForecastByCoordinates(
                    25.294741, 51.535293,
                    "", "", ""
                )
            } catch (e: Exception) {
                assertThat(e.localizedMessage, `is`("HTTP 401 Unauthorized"))
            }
        }
    }

    //test: verificar propiedad "hourly" tenga la longitud correcta (48 horas de pronostico)
    @Test
    fun checkHourlySizeTest() {
        runBlocking {
            mainViewModel.getWeatherAndForecast(
                25.294741, 51.535293,
                "09ed2cfc331705f6fd102606ed441669", "metric", "en"
            )
            val result = mainViewModel.getResult().getOrAwaitValue()
            assertThat(result.hourly.size, `is`(48))
        }
    }
}