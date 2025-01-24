package com.spotmap.spotmapandroid.Commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotmap.spotmapandroid.R
import androidx.compose.foundation.layout.Box


@Composable
fun CustomTextField(
    placeholder: String,
    textState: MutableState<String> = remember { mutableStateOf("") },
    onTextDidChange: () -> Unit,
    isPassword: Boolean = false
) {
    val modifier = Modifier.fillMaxWidth().height(40.dp).padding(0.dp, 0.dp)
        .background(color = colorResource(id = R.color.BackgroundColor), shape = RoundedCornerShape(8.dp))

    val textColor = colorResource(id = R.color.LightColor)
    val placeholderColor = colorResource(id = R.color.LightDarker2Color)
    val textSize = 15

    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {

        // Show placeholder text if textState is empty
        if (textState.value.isEmpty()) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = placeholder,
                style = TextStyle(fontSize = textSize.sp, color = placeholderColor)
            )
        }

        // BasicTextField for text input
        BasicTextField(
            modifier = Modifier.padding(start = 16.dp),
            value = textState.value,
            textStyle = TextStyle.Default.copy(fontSize = textSize.sp, color = textColor),
            cursorBrush = SolidColor(textColor),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            onValueChange = { newText ->
                textState.value = newText
                onTextDidChange() // Notify parent composable about text change
            }
        )
    }
}

@Composable
fun LargeCustomTextField(
    placeholder: String,
    textState: MutableState<String> = remember { mutableStateOf("") },
    onTextDidChange: () -> Unit,
    isPassword: Boolean = false
) {
    val modifier = Modifier.fillMaxWidth().height(160.dp).padding(0.dp, 0.dp)
        .background(color = colorResource(id = R.color.BackgroundColor), shape = RoundedCornerShape(8.dp))

    val textColor = colorResource(id = R.color.LightColor)
    val placeholderColor = colorResource(id = R.color.LightDarker2Color)
    val textSize = 15

    Box(modifier = modifier.padding(
        top = 10.dp,
        bottom = 10.dp),
        contentAlignment = Alignment.TopStart) {

        // Show placeholder text if textState is empty
        if (textState.value.isEmpty()) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = placeholder,
                style = TextStyle(fontSize = textSize.sp, color = placeholderColor)
            )
        }

        // BasicTextField for text input
        BasicTextField(
            modifier = Modifier.padding(start = 16.dp),
            value = textState.value,
            textStyle = TextStyle.Default.copy(fontSize = textSize.sp, color = textColor),
            cursorBrush = SolidColor(textColor),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            onValueChange = { newText ->
                textState.value = newText
                onTextDidChange() // Notify parent composable about text change
            }
        )
    }
}


@Preview(showBackground = false)
@Composable
fun PreviewCustomTextField() {

    LargeCustomTextField(
        placeholder = "Enter text here",
        onTextDidChange = {}
    )
}