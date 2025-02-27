package com.spotmap.spotmapandroid.Commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.spotmap.spotmapandroid.R

@Composable
fun UserImageView(modifier: Modifier = Modifier, url: String?, height: Dp, displayButton: Boolean = false, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(height)
            .clip(CircleShape)
    ) {

        ImageFromUrl(modifier = modifier
            .fillMaxSize()
            .clip(CircleShape),
            contentScale = ContentScale.Crop,
            url = url)

        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
                .clip(CircleShape),
            model = url ?: "",
            fallback = painterResource(id = R.drawable.placeholder_user),
            placeholder = painterResource(id = R.drawable.placeholder_user),
            contentDescription = null
        )
        if (displayButton == true) {
            Text(
                text = "Edit",
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.LightColor),
                modifier =  Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .align(Alignment.BottomCenter)
                    .background(colorResource(id = R.color.PrimaryColor))
                    .padding(2.dp)
            )
        }
    }
}