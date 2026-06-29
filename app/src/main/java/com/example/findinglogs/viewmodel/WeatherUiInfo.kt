package com.example.findinglogs.viewmodel

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.example.findinglogs.R
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.model.util.Utils

class WeatherUiInfo (
    val name: String,
    val tempActual: String,
    val tempMax: String,
    val tempMin: String,
    val pressure: String,
    val humidity: String,
    val weatherIcon: ImageBitmap,
    val cardBackgroundColor: Int,
) {
    constructor(weather: Weather, context: Context) : this(
        name = weather.name,
        tempActual = convertToCelsius(weather.main.temp),
        tempMax = convertToCelsius(weather.main.temp_max),
        tempMin = convertToCelsius(weather.main.temp_min),
        pressure = "${weather.main.pressure} hPa",
        humidity = "${weather.main.humidity}%",
        weatherIcon = getIcon(weather, context),
        cardBackgroundColor = getCardBackgroundColor(weather),
    )

    companion object {
        fun convertToCelsius(temp: Float): String {
            return Utils.getCelsiusTemperatureFromKevin(temp)
        }

        fun getIcon(weather: Weather, context: Context): ImageBitmap {
            val imageCode = weather.weather.get(0).icon ?: "02d"
            return Utils.getDrawable(imageCode, context).toBitmap().asImageBitmap()
        }

        fun getCardBackgroundColor (weather: Weather): Int {
            when (weather.weather.get(0).icon) {
                "02d" -> return R.color.weather_few_clouds
                "02n" -> return R.color.weather_few_clouds_dark
                "03d" -> return R.color.weather_cloudy
                "03n" -> return R.color.weather_cloudy_dark
                "04d" -> return R.color.weather_scattered_clouds
                "04n" -> return R.color.weather_scattered_clouds_dark
                "09d" -> return R.color.weather_fog
                "09n" -> return R.color.weather_fog_dark
                else -> return R.color.weather_few_clouds
            }
        }
    }
}