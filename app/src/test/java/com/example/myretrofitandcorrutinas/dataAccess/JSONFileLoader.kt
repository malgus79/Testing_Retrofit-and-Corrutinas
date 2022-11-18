package com.example.myretrofitandcorrutinas.dataAccess

import com.example.myretrofitandcorrutinas.entities.WeatherForecastEntity
import com.google.gson.Gson
import java.io.InputStreamReader

class JSONFileLoader {
    private var jsonStr: String? = null

    //para leer el archivo json
    fun loadJSONString(file: String): String?{
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return jsonStr
    }

    //transformar el json a las entidades creadas de la app
    fun loadWeatherForecastEntity(file: String): WeatherForecastEntity?{
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return Gson().fromJson(jsonStr, WeatherForecastEntity::class.java)
    }
}