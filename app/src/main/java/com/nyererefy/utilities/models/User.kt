package com.nyererefy.utilities.models

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") val id: Int,
        @SerializedName("username") val username: String,
        @SerializedName("name") val name: String,
        @SerializedName("university_id") val universityId: Int,
        @SerializedName("token") val token: String
)