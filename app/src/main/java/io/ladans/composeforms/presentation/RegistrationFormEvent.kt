package io.ladans.composeforms.presentation

sealed class RegistrationFormEvent {
    data class EmailChanged(val email: String): RegistrationFormEvent()
    data class PasswordChanged(val password: String): RegistrationFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String): RegistrationFormEvent()
    data class TermsAcceptanceChanged(val isTermsAccepted: Boolean): RegistrationFormEvent()
    object SubmitForm: RegistrationFormEvent()
}
