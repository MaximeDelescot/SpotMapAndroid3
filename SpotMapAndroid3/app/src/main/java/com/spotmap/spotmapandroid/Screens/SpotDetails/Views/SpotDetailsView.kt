package com.spotmap.spotmapandroid.Screens.SpotDetails.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Commons.CustomPageIndicator
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.GeneralButtonStyle
import com.spotmap.spotmapandroid.Commons.LargeTitleText
import com.spotmap.spotmapandroid.Commons.NormalText
import com.spotmap.spotmapandroid.Commons.SmallNormalText
import com.spotmap.spotmapandroid.Commons.TitleText
import com.spotmap.spotmapandroid.Commons.VerySmallNormalText
import com.spotmap.spotmapandroid.Screens.Map.Views.InfiniteCarousel
import com.spotmap.spotmapandroid.R
import java.security.acl.Group


@Composable
fun SpotDetailsView(spot: Spot) {

    val currentIndex = remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        InfiniteCarousel(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(4 / 3f),
            scrollTime = 3,
            scrollEnable = true,
            imageUrls = remember { mutableStateOf(spot.imageUrls) },
            currentIndex = currentIndex)

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp,
            vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Spacer(modifier = Modifier.weight(1f))

            CustomPageIndicator(
                modifier = Modifier.weight(2f),
                currentIndex = currentIndex,
                indexCount = spot.imageUrls.size)

            Row(modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End) {
                IconWithValueView(
                    iconId=R.drawable.ic_follow,
                    value = 12)
                Spacer(modifier = Modifier.width(4.dp))
                IconWithValueView(
                    iconId=R.drawable.ic_comment,
                    value = 12)
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start) {
            Row(verticalAlignment = Alignment.CenterVertically){
                SmallNormalText(spot.getType().toString().uppercase())
                Spacer(Modifier.weight(1f))
                VerySmallNormalText(
                    text = spot.getInfosText(),
                    color = colorResource(id = R.color.LightDarker1Color),
                    maxLine = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            TitleText(spot.name)
            SmallNormalText("Fake address bla 2 bla bla bla ")
            Spacer(Modifier.height(16.dp))
            if (!spot.description.isEmpty()) {
                NormalText(spot.description)
                Spacer(Modifier.height(16.dp))
            }
            Row {
                GeneralButton(
                    modifier = Modifier.weight(1f),
                    title = "Follow this spot",
                    style = GeneralButtonStyle.PRIMARY,
                    onClick = {})
                Spacer(modifier = Modifier.width(16.dp))
                GeneralButton(
                    modifier = Modifier.weight(1f),
                    title = "Upload a trick",
                    style = GeneralButtonStyle.SECONDARY,
                    onClick = {})
            }
        }
    }
}