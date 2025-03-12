package com.example.jetpack_weatherapp.data

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import com.example.jetpack_weatherapp.pages.ConnectivityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface ConnectivityRepository {
    val connectivityState: StateFlow<ConnectivityState>
}

class DefaultConnectivityRepository @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : ConnectivityRepository {
    private val _connectivityState =
        MutableStateFlow<ConnectivityState>(ConnectivityState.Unavailable)

    private val callback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _connectivityState.value = ConnectivityState.Available
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _connectivityState.value = ConnectivityState.Unavailable
        }
    }

    override val connectivityState: StateFlow<ConnectivityState> = _connectivityState.asStateFlow()

    init {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }
}