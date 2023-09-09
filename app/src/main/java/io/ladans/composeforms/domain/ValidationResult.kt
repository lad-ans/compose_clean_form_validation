package io.ladans.composeforms.domain

data class ValidationResult(
    val success: Boolean = false,
    val errorMessage: String? = null,
)
