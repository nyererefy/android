package com.nyererefy.utilities.models

import com.google.gson.annotations.SerializedName

data class AppUpdate(
        @SerializedName("version_code") val versionCode: Int,
        @SerializedName("version_name") val versionName: String,
        @SerializedName("new_features") val newFeatures: List<String>,
        @SerializedName("remained_days") val remainedDays: Int,
        @SerializedName("warning") val warning: String
)