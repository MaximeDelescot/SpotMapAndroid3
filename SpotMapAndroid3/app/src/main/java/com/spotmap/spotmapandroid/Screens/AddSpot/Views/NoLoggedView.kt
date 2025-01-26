package com.spotmap.spotmapandroid.Screens.AddSpot.Views

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.LargeTitleText
import com.spotmap.spotmapandroid.Commons.NormalText
import com.spotmap.spotmapandroid.Commons.TitleText
import com.spotmap.spotmapandroid.R

@Composable
fun NoLoggedView() {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_account),
                contentDescription = "logo app",
                modifier = Modifier.size(90.dp),
                colorFilter = ColorFilter.tint(colorResource(id= R.color.LightColor))
            )
            BasicSpacer()
            TitleText("You need to be logged to create a new spot.", textAlign = TextAlign.Center)
            Spacer(modifier= Modifier.height(4.dp))
            NormalText("You can create an account for free in the Account tab.", textAlign = TextAlign.Center, color = colorResource(id=R.color.LightDarker1Color ))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = false)
@Composable
fun previewNoLogged() {
    NoLoggedView()
}