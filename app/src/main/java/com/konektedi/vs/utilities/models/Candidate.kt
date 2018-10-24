package com.konektedi.vs.utilities.models

import com.google.gson.annotations.SerializedName

data class Candidate(
        @SerializedName("candidate_id") val candidateId: String,
        @SerializedName("id") val id: String,
        @SerializedName("category_id") val categoryId: String,
        @SerializedName("biography") val biography: Any,
        @SerializedName("cover") val cover: String,
        @SerializedName("election_id") val electionId: String,
        @SerializedName("name") val name: String,
        @SerializedName("year") val year: String,
        @SerializedName("abbr") val abbr: String
)