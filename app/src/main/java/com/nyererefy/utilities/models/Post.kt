package com.nyererefy.utilities.models

import com.google.gson.annotations.SerializedName

data class Post(
        @SerializedName("title") val title: String,
        @SerializedName("post") val post: String,
        @SerializedName("post_id") val postId: String,
        @SerializedName("time") val time: String,
        @SerializedName("name") val name: String
)