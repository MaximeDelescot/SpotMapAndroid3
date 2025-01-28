package com.spotmap.spotmapandroid.Screens.Map.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.R

@Composable
fun SpotView(modifier: Modifier = Modifier, spot: MutableState<Spot?>) {
    Box(
        modifier = modifier.fillMaxSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.SecondaryColor))) {

        Row(modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Carousel()
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.fillMaxHeight().weight(1f)) {
                Text(spot.value?.getType().toString().uppercase(), color = colorResource(id= R.color.LightColor))
                Text(spot.value?.name ?: "", color = colorResource(id= R.color.LightColor))
            }
            Spacer(Modifier.width(16.dp))
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "arrow next",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.PrimaryColor)) )
        }
    }
}

@Composable
fun Carousel() {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
            .fillMaxHeight()
            .aspectRatio(1f)
            .background(colorResource(id=R.color.BackgroundColor))
    ) {


    }
}
