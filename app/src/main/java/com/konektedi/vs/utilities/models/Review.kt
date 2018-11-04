package com.konektedi.vs.utilities.models

import com.google.gson.annotations.SerializedName

data class Review(
        @SerializedName("review") val review: String,
        @SerializedName("review_id") val reviewId: Int,
        @SerializedName("time") val time: String,
        @SerializedName("username") val username: String,
        @SerializedName("id") val id: Int,
        @SerializedName("dp") val dp: String?
)