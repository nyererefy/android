

package com.nyererefy.utilities.common

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,     // Failed to connect to the server or any unknown reasons.
    EMPTY,      // Empty response like 404
    ERROR       // Connected and got Error response from server. This comes with message.
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val msg: String? = null) {

    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        val END = NetworkState(Status.EMPTY)
        val FAILED = NetworkState(Status.FAILED)
        val ERROR = NetworkState(Status.ERROR)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)

        //Returns error message from the server. Used in Repositories.
        fun serverMsg(msg: String?) = NetworkState(Status.ERROR, msg)
    }
}