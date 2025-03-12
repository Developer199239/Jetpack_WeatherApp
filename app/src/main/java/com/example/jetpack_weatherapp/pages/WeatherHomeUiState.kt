package com.example.jetpack_weatherapp.pages

import com.example.jetpack_weatherapp.data.CurrentWeather
import com.example.jetpack_weatherapp.data.ForecastWeather

data class Weather(
    val currentWeather: CurrentWeather,
    val forecastWeather: ForecastWeather
)

sealed interface WeatherHomeUiState {
    data class Success(val weather: Weather) : WeatherHomeUiState
    data object Error : WeatherHomeUiState
    data object Loading : WeatherHomeUiState
}

sealed interface ConnectivityState {
    data object Available : ConnectivityState
    data object Unavailable : ConnectivityState
}