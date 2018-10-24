package com.konektedi.vs.utilities.models

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") val id: String,
        @SerializedName("username") val username: String,
        @SerializedName("name") val name: String,
        @SerializedName("university_id") val universityId: String
)