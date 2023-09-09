package io.ladans.composeforms

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.ladans.composeforms.presentation.MainViewModel
import io.ladans.composeforms.presentation.RegistrationFormEvent
import io.ladans.composeforms.ui.theme.ComposeFormsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFormsTheme {
                Surface{

                    val viewModel = viewModel<MainViewModel>()
                    val state = viewModel.state
                    val context = LocalContext.current
                    
                    LaunchedEffect(key1 = context) {
                        viewModel.validationEvents.collect { event ->
                            when(event) {
                                is MainViewModel.ValidationEvent.Success -> {
                                    Toast.makeText(
                                        context,
                                        "Registro efetuado com sucesso",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextField(
                            value = state.email,
                            isError = state.emailError != null,
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "E-mail")
                            },
                            leadingIcon = {
                              Image(imageVector = Icons.Default.Email, contentDescription = null)
                            },
                            onValueChange = {
                                viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                        )
                        GenerateError(state.emailError)
                        Spacer(modifier = Modifier.height(20.dp))
                        TextField(
                            value = state.password,
                            isError = state.passwordError != null,
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Senha")
                            },
                            leadingIcon = {
                                Image(imageVector = Icons.Default.Lock, contentDescription = null)
                            },
                            onValueChange = {
                                viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                        )
                        GenerateError(state.passwordError)
                        Spacer(modifier = Modifier.height(20.dp))
                        TextField(
                            value = state.repeatedPassword,
                            isError = state.repeatedPasswordError != null,
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Confirmar Senha")
                            },
                            leadingIcon = {
                                Image(imageVector = Icons.Default.Lock, contentDescription = null)
                            },
                            onValueChange = {
                                viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                        )
                        GenerateError(state.repeatedPasswordError)
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = state.isTermsAccepted,
                                onCheckedChange = {
                                    viewModel.onEvent(
                                        RegistrationFormEvent.TermsAcceptanceChanged(it)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(30.dp))
                            Text(text = "Aceitar Termos")
                        }
                        GenerateError(state.termsError)
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                viewModel.onEvent(RegistrationFormEvent.SubmitForm)
                            }
                        ) {
                            Text("Submeter", color = Color(0xffffffff))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenerateError(error: String? = null) {
    if (error != null) {
        Column(horizontalAlignment = Alignment.Start) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    }
}