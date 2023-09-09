package io.ladans.composeforms.domain.use_cases

import android.util.Patterns
import io.ladans.composeforms.domain.ValidationResult

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(errorMessage = "O email não pode estar vazio");
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(errorMessage = "O email é inválido")
        }

        return ValidationResult(success = true)
    }
}