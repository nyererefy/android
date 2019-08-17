package com.nyererefy.utilities.models

import com.google.gson.annotations.SerializedName

data class Motion(
        @SerializedName("title") val title: String,
        @SerializedName("motion") val motion: String,
        @SerializedName("motion_id") val motionId: Int,
        @SerializedName("time") val time: String,
        @SerializedName("name") val name: String,
        @SerializedName("participated") val participated: Int = 0,
        @SerializedName("no_counts") val noCounts: String,
        @SerializedName("yes_counts") val yesCounts: String,
        @SerializedName("opinions_counts") val opinionsCounts: String
)