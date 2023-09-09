package io.ladans.composeforms.domain.use_cases

import io.ladans.composeforms.domain.ValidationResult

class ValidateRepeatedPassword {
    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(errorMessage = "As senhas não coincidem")
        }

        return ValidationResult(success = true)
    }
}