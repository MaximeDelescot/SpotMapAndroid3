package com.spotmap.spotmapandroid.Commons

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.spotmap.spotmapandroid.R

@Composable
fun VerySmallNormalText(modifier: Modifier = Modifier, text: String, color: Color = colorResource(id = R.color.LightColor), textAlign: TextAlign? = null, maxLine: Int = Int.MAX_VALUE, overflow: TextOverflow = TextOverflow.Clip) {
    Text(modifier = modifier,
        text = text,
        textAlign = textAlign,
        color = color,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        maxLines = maxLine,
        overflow = overflow)
}

@Composable
fun SmallNormalText(text: String, color: Color = colorResource(id = R.color.LightColor), maxLine: Int = Int.MAX_VALUE, textAlign: TextAlign? = null) {
    Text(text,
        textAlign = textAlign,
        color = color,
        fontWeight = FontWeight.Normal,
        maxLines = maxLine,
        fontSize = 12.sp)
}


@Composable
fun NormalText(text: String, color: Color = colorResource(id = R.color.LightColor), maxLine: Int = Int.MAX_VALUE, textAlign: TextAlign? = null) {
    Text(text,
        textAlign = textAlign,
        color = color,
        fontWeight = FontWeight.Normal,
        maxLines = maxLine,
        fontSize = 15.sp)
}

@Composable
fun TitleText(modifier: Modifier = Modifier, text: String, color: Color = colorResource(id = R.color.LightColor), maxLine: Int = Int.MAX_VALUE, textAlign: TextAlign? = null) {
    Text(text,
        modifier = modifier,
        textAlign = textAlign,
        color = color,
        fontWeight = FontWeight.Bold,
        maxLines = maxLine,
        fontSize = 15.sp)
}

@Composable
fun LargeTitleText(text: String, color: Color = colorResource(id = R.color.LightColor), maxLine: Int = Int.MAX_VALUE, textAlign: TextAlign? = null) {
    Text(text,
        textAlign = textAlign,
        color = color,
        fontWeight = FontWeight.Bold,
        maxLines = maxLine,
        fontSize = 19.sp)
}