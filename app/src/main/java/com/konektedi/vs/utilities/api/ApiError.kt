package com.konektedi.vs.utilities.api

data class ApiError(
        val statusCode: Int = 0,
        val message: String = "",
        val value: Int? = null
)

