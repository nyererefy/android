package com.nyererefy.utilities.model


data class CandidateUserProfile(
        val name: String?
)

data class CandidateAndVotesCount(
        val id: String,
        val votesCount: Int,
        val avatar: String?,
        val user: CandidateUserProfile
)