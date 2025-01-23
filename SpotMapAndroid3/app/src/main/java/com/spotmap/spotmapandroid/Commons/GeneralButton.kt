package com.spotmap.spotmapandroid.Commons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotmap.spotmapandroid.R


enum class GeneralButtonStyle {
    PRIMARY, SECONDARY
}

@Composable
fun GeneralButton(title: String,
                  onClick: () -> Unit,
                  isEnable: Boolean = true,
                  style: GeneralButtonStyle = GeneralButtonStyle.PRIMARY) {

    val modifier = Modifier.fillMaxWidth().height(40.dp)
    val containerColor = if (style == GeneralButtonStyle.PRIMARY) { colorResource(id = R.color.PrimaryColor) } else { colorResource(id = R.color.BackgroundColor) }
    val contentColor = colorResource(id = R.color.LightColor)

    Button(modifier = modifier,
        enabled = isEnable,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(0.3f),
            disabledContentColor = contentColor.copy(0.3f)
        ),
        onClick = {
            onClick()
        }) {
        Text(title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewGeneralButton() {
    GeneralButton(
        title = "Sign in", onClick = {

        }
    )
}