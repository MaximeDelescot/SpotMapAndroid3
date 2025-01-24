package com.spotmap.spotmapandroid.Commons

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.spotmap.spotmapandroid.R

@Composable
fun TitleText(text: String) {
    Text(text,
        color = colorResource(id = R.color.LightColor),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp)
}