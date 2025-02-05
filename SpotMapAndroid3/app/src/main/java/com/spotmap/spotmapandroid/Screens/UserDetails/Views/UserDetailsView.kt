package com.spotmap.spotmapandroid.Screens.UserDetails.Views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.spotmap.spotmapandroid.Class.Skater
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.GeneralButtonStyle
import com.spotmap.spotmapandroid.Commons.LargeTitleText
import com.spotmap.spotmapandroid.Commons.SmallNormalText
import com.spotmap.spotmapandroid.Commons.TitleText
import com.spotmap.spotmapandroid.Commons.Utils.convertToFastestUrl
import com.spotmap.spotmapandroid.Commons.VerySmallNormalText
import com.spotmap.spotmapandroid.R

@Composable
fun UserDetailsView(user: Skater, editClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start)    {
            UserImageView(
                modifier = Modifier,
                url = user.photoUrl,
                height = 80.dp,
                displayButton = true,
                onClick = editClick)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TitleText(
                            text = user.userName,
                            color = colorResource(id = R.color.LightColor))
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {

                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = "logo app",
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.LightDarker2Color)) )
                    }
                }
                Row {
                    UserCountView(
                        modifier= Modifier.weight(1f),
                        count = 12,
                        title = "Video")
                    UserCountView(
                        modifier= Modifier.weight(1f),
                        count = 12,
                        title = "Followers")
                    UserCountView(
                        modifier= Modifier.weight(1f),
                        count = 12,
                        title = "Follows")
                    UserCountView(
                        modifier= Modifier.weight(1f),
                        count = 12,
                        title = "Fav spots")
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        GeneralButton(
            title = "Follow",
            onClick = {},
            style = GeneralButtonStyle.PRIMARY)
    }
}
@Composable
fun UserCountView(modifier: Modifier, count: Int, title: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start) {
        TitleText(text = count.toString())
        VerySmallNormalText(text = title,
            color = colorResource(id = R.color.LightDarker1Color))
    }
}

@Composable
fun UserImageView(modifier: Modifier, url: String?, height: Dp, displayButton: Boolean = false, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(height)
            .clip(CircleShape)
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
                .clip(CircleShape),
            model = url?.convertToFastestUrl(),
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
