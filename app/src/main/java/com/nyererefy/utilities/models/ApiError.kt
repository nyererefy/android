package com.nyererefy.utilities.models

import com.google.gson.annotations.SerializedName

data class ApiError(
        @SerializedName("code") val statusCode: Int = 0,
        @SerializedName("message") val message: String = "",
        @SerializedName("value") val value: Int? = null
)

