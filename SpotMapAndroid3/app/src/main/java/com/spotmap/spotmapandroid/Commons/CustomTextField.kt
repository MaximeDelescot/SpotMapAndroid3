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

@Composable
fun CustomTextField(placeholder: String,
                    textState: MutableState<String> = remember { mutableStateOf("") },
                    onTextDidChange: () -> Unit, isPassword: Boolean = false) {

    val modifier = Modifier.fillMaxWidth().height(40.dp).padding(0.dp, 0.dp)
        .background(color = colorResource(id = R.color.BackgroundColor), shape = RoundedCornerShape(8.dp))

    val textColor = colorResource(id = R.color.LightColor)
    val placeholderColor = colorResource(id = R.color.LightDarker2Color)
    val textSize = 15

    var text = remember { mutableStateOf("") }

    Box(modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {

        if (textState.value.isEmpty()) {
            Text(modifier= Modifier.padding(start = 16.dp),
                text = placeholder,
                style = TextStyle(fontSize = textSize.sp, color = placeholderColor))
        }

        BasicTextField(modifier = Modifier.padding(start = 16.dp),
            value = textState.value,
            textStyle = TextStyle.Default.copy(fontSize = textSize.sp, color = textColor ),
            cursorBrush = SolidColor(textColor),
            visualTransformation = if (isPassword==true) { PasswordVisualTransformation() } else { VisualTransformation.None},
            onValueChange = { newText ->
                textState.value = newText
                onTextDidChange()
            })
    }
}

@Composable
fun Box(modifier: Modifier, contentAlignment: Alignment, content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}

@Preview(showBackground = false)
@Composable
fun PreviewCustomTextField() {

    CustomTextField(
        placeholder = "Enter text here",
        onTextDidChange = {}
    )
}