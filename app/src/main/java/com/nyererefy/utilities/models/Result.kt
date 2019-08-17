package com.nyererefy.utilities.models

import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("name") val name: String,
        @SerializedName("votes") val votes: Int,
        @SerializedName("cover") val cover: String? = null
)