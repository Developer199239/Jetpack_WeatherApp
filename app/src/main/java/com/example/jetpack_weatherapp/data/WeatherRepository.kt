package com.example.jetpack_weatherapp.data

import com.example.jetpack_weatherapp.network.WeatherApiService
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getCurrentWeather(endUrl: String): CurrentWeather
    suspend fun getForecastWeather(endUrl: String): ForecastWeather
}

class WeatherRepositoryImpl @Inject constructor(
    private val  weatherApiService: WeatherApiService
) : WeatherRepository {
    override suspend fun getCurrentWeather(endUrl: String): CurrentWeather {
        return  weatherApiService.getCurrentWeather(endUrl)
    }

    override suspend fun getForecastWeather(endUrl: String): ForecastWeather {
        return  weatherApiService.getForecastWeather(endUrl)
    }

}