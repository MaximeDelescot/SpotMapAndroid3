package com.spotmap.spotmapandroid.Screens.Account.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.CustomTextField
import com.spotmap.spotmapandroid.Commons.GeneralButton


@Composable
fun SignUpView(tryToSignUp: (email: String, password: String, username: String) -> Unit) {
    Column(verticalArrangement = Arrangement.Center) {

        val emailTextFieldState = remember { mutableStateOf("") }
        val passwordTextFieldState = remember { mutableStateOf("") }
        val passwordConfirmationTextFieldState = remember { mutableStateOf("") }
        val usernameTextFieldState = remember { mutableStateOf("") }

        var buttonIsEnable = remember { mutableStateOf(false) }

        fun checkForButtonStatus() {
            buttonIsEnable.value =
                if (emailTextFieldState.value.isNotEmpty()
                    && passwordTextFieldState.value.isNotEmpty()
                    && usernameTextFieldState.value.isNotEmpty()
                    && passwordConfirmationTextFieldState.value.isNotEmpty()) {
                    true
                } else {
                    false
                }
        }

        CustomTextField(
            placeholder = "Username",
            textState = usernameTextFieldState,
            onTextDidChange = { checkForButtonStatus() })
        BasicSpacer()
        CustomTextField(
            placeholder = "Email",
            textState = emailTextFieldState,
            onTextDidChange = { checkForButtonStatus() })
        BasicSpacer()
        CustomTextField(
            placeholder = "Password",
            textState = passwordTextFieldState,
            onTextDidChange = { checkForButtonStatus() },
            isPassword = true)
        BasicSpacer()
        CustomTextField(
            placeholder = "Confirm password",
            textState = passwordConfirmationTextFieldState,
            onTextDidChange = { checkForButtonStatus() },
            isPassword = true)
        BasicSpacer()
        GeneralButton(
            title="Sign up",
            isEnable = buttonIsEnable.value,
            onClick = {
                tryToSignUp(emailTextFieldState.value,
                    passwordTextFieldState.value,
                    usernameTextFieldState.value)
            })
    }
}
