package com.spotmap.spotmapandroid.Screens.SpotDetails.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.Commons.SmallNormalText
import com.spotmap.spotmapandroid.R

@Composable
fun IconWithValueView(iconId: Int, value: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconId),
            contentDescription = "logo app",
            colorFilter = ColorFilter.tint(colorResource(id = R.color.LightColor)) )

        SmallNormalText(value.toString())
    }
}
@Preview
@Composable
fun previewIconWithValueView() {
    IconWithValueView(iconId = R.drawable.ic_plus,
        value = 12)
}
