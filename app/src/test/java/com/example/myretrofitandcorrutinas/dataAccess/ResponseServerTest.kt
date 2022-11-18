package com.example.myretrofitandcorrutinas.dataAccess

import com.example.myretrofitandcorrutinas.entities.WeatherForecastEntity
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class ResponseServerTest {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()  //arrancar el servidor
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()  //liberar el recurso despues de cada prueba
    }

    //test: garantizar de que se esta leyendo el archivo json correctamente
    @Test
    fun `read json file success`(){
        val reader = JSONFileLoader().loadJSONString("weather_forecast_response_success")
        //comprobar que la lectura no sea null
        assertThat(reader, `is`(notNullValue()))
        //comprobar cualqueir dato del json (ej: timezone)
        assertThat(reader, containsString("Asia/Qatar"))
    }

    //utilizando Mock Web Server
    //test: procesar el json tal cual viene del servidor pero de manera local
    @Test
    fun `get weatherForecast and check timezone exist`(){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(JSONFileLoader().loadJSONString("weather_forecast_response_success")
                ?: "{errorCode:34}")
        mockWebServer.enqueue(response)

        //validar que tenga la propiedad "timezone"
        assertThat(response.getBody()?.readUtf8(), containsString("\"timezone\""))
    }
}