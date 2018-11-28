package com.konektedi.vs.utilities.models

import com.google.gson.annotations.SerializedName

data class Candidate(
        @SerializedName("candidate_id") val candidateId: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("category_id") val categoryId: Int,
        @SerializedName("biography") val biography: String? = null,
        @SerializedName("cover") val cover: String,
        @SerializedName("election_id") val electionId: Int,
        @SerializedName("name") val name: String,
        @SerializedName("username") val username: String,
        @SerializedName("year") val year: String,
        @SerializedName("abbr") val abbr: String
)