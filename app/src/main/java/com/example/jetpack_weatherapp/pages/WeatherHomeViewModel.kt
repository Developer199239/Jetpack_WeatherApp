package com.example.jetpack_weatherapp.pages

import android.app.Application
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jetpack_weatherapp.data.ConnectivityRepository
import com.example.jetpack_weatherapp.data.CurrentWeather
import com.example.jetpack_weatherapp.data.DefaultConnectivityRepository
import com.example.jetpack_weatherapp.data.ForecastWeather
import com.example.jetpack_weatherapp.data.WeatherRepository
import com.example.jetpack_weatherapp.data.WeatherRepositoryImpl
import com.example.jetpack_weatherapp.utils.WEATHER_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeViewModel @Inject constructor(
    private val connectivityRepository: ConnectivityRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel(){
    var uiState: WeatherHomeUiState by mutableStateOf(WeatherHomeUiState.Loading)
    private var latitude = 0.0
    private var longitude = 0.0
    val connectivityState: StateFlow<ConnectivityState> = connectivityRepository.connectivityState
    private val execptionHandller = CoroutineExceptionHandler {_, throwable ->
        uiState = WeatherHomeUiState.Error
    }

    fun setLocation(lat: Double, long: Double) {
        latitude = lat
        longitude = long
    }

    fun getWeatherData(){
        viewModelScope.launch(execptionHandller) {
            uiState =try {
               val currentWeather = async { getCurrentData() }.await()
               val forecastWeather = async { getForecastData() }.await()
                Log.d("WeatherHomeViewModel", "currentData: ${currentWeather.main!!.temp}")
                Log.d("WeatherHomeViewModel", "forecast: ${forecastWeather.list!!.size}")
                WeatherHomeUiState.Success(Weather(currentWeather,forecastWeather))
            }catch (e: Exception) {
                WeatherHomeUiState.Error
            }
        }
    }

    private suspend fun getCurrentData() : CurrentWeather {
        val endUrl = "weather?lat=$latitude&lon=$longitude&appid=$WEATHER_API_KEY&units=imperial";
        return weatherRepository.getCurrentWeather(endUrl)
    }

    private suspend fun getForecastData() : ForecastWeather {
        val endUrl = "forecast?lat=$latitude&lon=$longitude&appid=$WEATHER_API_KEY&units=imperial";
        return weatherRepository.getForecastWeather(endUrl)
    }
}