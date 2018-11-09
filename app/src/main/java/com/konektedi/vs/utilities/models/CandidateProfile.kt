package com.konektedi.vs.utilities.models

import com.google.gson.annotations.SerializedName

data class CandidateProfile(
        @SerializedName("details") val details: Candidate,
        @SerializedName("images") val images: List<Image>
)