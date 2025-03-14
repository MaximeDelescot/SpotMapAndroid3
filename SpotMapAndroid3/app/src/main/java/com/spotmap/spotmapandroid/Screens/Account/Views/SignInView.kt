package com.spotmap.spotmapandroid.Screens.Account.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.CustomTextField
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.R

@Composable
fun SignInView(forgotPasswordButtonTapped: () -> Unit,
               tryToSignIn: (email: String, password: String) -> Unit) {

    val emailTextFieldState = remember { mutableStateOf("") }
    val passwordTextFieldState = remember { mutableStateOf("") }

    var buttonIsEnable = remember { mutableStateOf(false) }

    fun checkForButtonStatus() {
        buttonIsEnable.value = if (emailTextFieldState.value.isNotEmpty() && passwordTextFieldState.value.isNotEmpty()) {
            true
        } else {
            false
        }
    }

    Column(verticalArrangement = Arrangement.Center) {

        CustomTextField(placeholder = "Email",
            textState = emailTextFieldState,
            onTextDidChange = { checkForButtonStatus() }
        )
        BasicSpacer()
        CustomTextField(placeholder = "Password",
            textState = passwordTextFieldState,
            onTextDidChange ={ checkForButtonStatus() },
            isPassword = true
        )
        BasicSpacer()
        GeneralButton(
            title="Sign in",
            onClick = { tryToSignIn(emailTextFieldState.value, passwordTextFieldState.value) },
            isEnable = buttonIsEnable.value )
        TextButton(
            onClick = { forgotPasswordButtonTapped() },
            modifier = Modifier.fillMaxWidth()) {
            Text("Forgot password?",
                color = colorResource(id = R.color.LightDarker2Color))
        }
    }
}
