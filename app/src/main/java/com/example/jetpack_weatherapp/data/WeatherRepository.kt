package com.example.jetpack_weatherapp.data

import com.example.jetpack_weatherapp.network.WeatherApi

interface WeatherRepository {
    suspend fun getCurrentWeather(endUrl: String): CurrentWeather
    suspend fun getForecastWeather(endUrl: String): ForecastWeather
}

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getCurrentWeather(endUrl: String): CurrentWeather {
        return  WeatherApi.retrofitService.getCurrentWeather(endUrl)
    }

    override suspend fun getForecastWeather(endUrl: String): ForecastWeather {
        return  WeatherApi.retrofitService.getForecastWeather(endUrl)
    }

}