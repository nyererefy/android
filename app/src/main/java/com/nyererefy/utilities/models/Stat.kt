package com.nyererefy.utilities.models

import com.google.gson.annotations.SerializedName

data class Stat(
        @SerializedName("title") val title: String,
        @SerializedName("count") val count: String
)