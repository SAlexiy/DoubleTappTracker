package com.salexey.network_tracker.network

sealed class NetworkState {
    object Fetched : NetworkState()
    object Error : NetworkState()
}