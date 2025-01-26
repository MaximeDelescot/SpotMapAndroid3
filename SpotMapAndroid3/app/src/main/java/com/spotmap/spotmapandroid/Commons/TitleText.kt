package com.spotmap.spotmapandroid.Commons

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.spotmap.spotmapandroid.R


@Composable
fun NormalText(text: String, color: Color = colorResource(id = R.color.LightColor), textAlign: TextAlign? = null) {
    Text(text,
        textAlign = textAlign,
        color = color,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp)
}

@Composable
fun TitleText(text: String, color: Color = colorResource(id = R.color.LightColor), textAlign: TextAlign? = null) {
    Text(text,
        textAlign = textAlign,
        color = color,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp)
}

@Composable
fun LargeTitleText(text: String, color: Color = colorResource(id = R.color.LightColor), textAlign: TextAlign? = null) {
    Text(text,
        textAlign = textAlign,
        color = color,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp)
}