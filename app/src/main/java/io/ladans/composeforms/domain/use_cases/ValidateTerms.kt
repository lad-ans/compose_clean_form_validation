package io.ladans.composeforms.domain.use_cases

import io.ladans.composeforms.domain.ValidationResult

class ValidateTerms {
    fun execute(isTermsAccepted: Boolean): ValidationResult {
        if (!isTermsAccepted) {
            return ValidationResult(errorMessage = "Aceite os termos, por favor")
        }

        return ValidationResult(success = true)
    }
}