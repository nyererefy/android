package com.nyererefy.utilities

/**
 * Data validation state of the setup form.
 */
data class SetUpInfoFormState(
        val nameError: Int? = null,
        val usernameError: Int? = null,
        val passwordError: Int? = null,
        val isDataValid: Boolean = false
)
