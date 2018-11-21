package com.konektedi.vs.utilities.models

import com.google.gson.annotations.SerializedName

data class Opinion(
        @SerializedName("author") val authorID: Int,
        @SerializedName("opinion") val opinion: String,
        @SerializedName("opinion_id") val opinionId: Int,
        @SerializedName("time") val time: String,
        @SerializedName("username") val username: String,
        @SerializedName("vote") val vote: String
)