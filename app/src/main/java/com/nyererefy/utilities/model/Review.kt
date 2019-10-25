package com.nyererefy.utilities.model

data class User(
        val id: String,
        val avatar: String?,
        val name: String?
)

data class Review(
        val id: String,
        val content: String,
        val user: User
)