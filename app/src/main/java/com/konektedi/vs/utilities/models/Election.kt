package com.konektedi.vs.utilities.models

import com.google.gson.annotations.SerializedName

data class Election(
        @SerializedName("election_title") val electionTitle: String,
        @SerializedName("election_id") val electionId: Int,
        @SerializedName("opened") val opened: Int = 0
)