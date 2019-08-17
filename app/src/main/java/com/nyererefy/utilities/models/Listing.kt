package com.nyererefy.utilities.models

import com.google.gson.annotations.SerializedName

data class Listing(
        @SerializedName("candidates") val candidates: List<Candidate>,
        @SerializedName("states") val category: Category
)