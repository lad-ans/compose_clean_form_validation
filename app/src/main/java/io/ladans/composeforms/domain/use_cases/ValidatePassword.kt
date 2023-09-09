package io.ladans.composeforms.domain.use_cases

import io.ladans.composeforms.domain.ValidationResult

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(errorMessage = "A Senha deve ter no mínimo 8 caracteres");
        }

        val containsLettersAndDigits =
            password.any {it.isDigit()} && password.any {it.isLetter()}

        if (!containsLettersAndDigits) {
            return ValidationResult(errorMessage = "A senha deve ter no mínimo um número")
        }

        return ValidationResult(success = true)
    }
}