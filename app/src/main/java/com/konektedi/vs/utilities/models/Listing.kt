package com.konektedi.vs.utilities.models

import com.google.gson.annotations.SerializedName

data class Listing(
        @SerializedName("candidates") val candidates: List<Candidate>,
        @SerializedName("category") val category: Category
)