package com.spotmap.spotmapandroid.Commons

import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.R

@Composable
fun CustomPageIndicator(modifier: Modifier = Modifier, currentIndex: MutableState<Int>, indexCount: Int) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 0 until indexCount) {
            CustomIndicator(isSelected = (i == currentIndex.value))
            if (i < indexCount - 1) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}

@Composable
fun CustomIndicator(isSelected: Boolean) {

    val color = if (isSelected) {colorResource(id = R.color.PrimaryColor)} else {colorResource(id = R.color.LightColor)}
    Box(
        modifier = Modifier
            .size(width = 8.dp, height = 8.dp)
            .clip(CircleShape)
            .background(color = color)
    )
}
