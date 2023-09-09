package io.ladans.composeforms.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ladans.composeforms.domain.use_cases.ValidateEmail
import io.ladans.composeforms.domain.use_cases.ValidatePassword
import io.ladans.composeforms.domain.use_cases.ValidateRepeatedPassword
import io.ladans.composeforms.domain.use_cases.ValidateTerms
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateTerms: ValidateTerms = ValidateTerms(),
): ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.TermsAcceptanceChanged -> {
                state = state.copy(isTermsAccepted = event.isTermsAccepted)
            }
            is RegistrationFormEvent.SubmitForm -> {
                submitForm()
            }
        }
    }

    private fun submitForm() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            state.password,
            state.repeatedPassword
        )
        val termsAcceptanceResult = validateTerms.execute(state.isTermsAccepted)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsAcceptanceResult,
        ).any { it.errorMessage != null }

        state = state.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            repeatedPasswordError = repeatedPasswordResult.errorMessage,
            termsError = termsAcceptanceResult.errorMessage,
        )
        if (hasError) return

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}