package com.nyererefy.utilities.models

import com.google.gson.annotations.SerializedName

data class Category(
        @SerializedName("category") val category: String,
        @SerializedName("category_id") val categoryId: Int,
        @SerializedName("election_id") val electionId: Int,
        @SerializedName("voted") val hasVoted: Int = 0,
        @SerializedName("opened") val isOpened: Int = 0
)