package com.nyererefy.utilities.common

enum class Status {
    LOADING,
    END,
    SUCCESS,
    FAILED,
    EMPTY,
    ERROR
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val msg: String? = null) {

    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.LOADING)
        val END = NetworkState(Status.END)
        val FAILED = NetworkState(Status.FAILED)
        val EMPTY = NetworkState(Status.EMPTY)
        val ERROR = NetworkState(Status.ERROR)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}