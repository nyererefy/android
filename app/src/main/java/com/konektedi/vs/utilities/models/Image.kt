package com.konektedi.vs.utilities.models

import com.google.gson.annotations.SerializedName

data class Image(
        @SerializedName("image") val image: String,
        @SerializedName("image_id") val imageId: Int
)