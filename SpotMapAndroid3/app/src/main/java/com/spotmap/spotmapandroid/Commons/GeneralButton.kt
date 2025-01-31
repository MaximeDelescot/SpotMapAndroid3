package com.spotmap.spotmapandroid.Commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotmap.spotmapandroid.R


enum class GeneralButtonStyle {
    PRIMARY, BACKGROUND, SECONDARY
}

@Composable
fun GeneralButton(modifier: Modifier = Modifier,
                  title: String,
                  onClick: () -> Unit,
                  isEnable: Boolean = true,
                  style: GeneralButtonStyle = GeneralButtonStyle.PRIMARY) {

    val containerColor = when(style) {
        GeneralButtonStyle.PRIMARY -> colorResource(id = R.color.PrimaryColor)
        GeneralButtonStyle.BACKGROUND -> colorResource(id = R.color.BackgroundColor)
        GeneralButtonStyle.SECONDARY -> colorResource(id = R.color.SecondaryColor)
    }

    val contentColor = colorResource(id = R.color.LightColor)

    Button(modifier = modifier
        .fillMaxWidth()
        .height(40.dp),
        enabled = isEnable,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(0.3f),
            disabledContentColor = contentColor.copy(0.3f)
        ),
        onClick = onClick
        ) {
        Text(title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )
    }
}


@Composable
fun IconBorderButton(
    modifier: Modifier = Modifier,
    iconResId: Int,
    onClick: () -> Unit
) {

    IconButton(
        onClick = onClick,
        modifier = modifier.size(55.dp)
            .background(color = colorResource(id = R.color.SecondaryColor), shape = CircleShape)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = colorResource(id = R.color.LightColor),
            modifier = modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewIconBorderButton() {

    IconBorderButton(iconResId = R.drawable.ic_location) { }

}