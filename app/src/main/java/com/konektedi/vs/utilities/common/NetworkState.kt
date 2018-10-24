/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.konektedi.vs.utilities.common

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,     // Failed to connect to the server or any unknown reasons.
    EMPTY,      // Empty response like 404
    ERROR       // Connected and got Error response from server. This comes with message.
}

// TODO Getting all error messages as response from server and displaying. Casting required here.

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