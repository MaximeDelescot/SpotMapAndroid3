package com.spotmap.spotmapandroid.Screens.AddSpot.Views

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.CustomPageIndicator
import com.spotmap.spotmapandroid.Commons.CustomTextField
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.GeneralButtonStyle
import com.spotmap.spotmapandroid.Commons.TitleText
import com.spotmap.spotmapandroid.R

@Composable
fun AddImagesView(modifier: Modifier = Modifier,
                  currentIndex: Int,
                  numberOfPage: Int,
                  previousButtonTapped: ()->Unit = {},
                  creationButtonTapped: ()->Unit = {},
                  imageSelected: SnapshotStateList<Uri>) {

    TitleText(text = "Select images (3/3)")
    BasicSpacer()
    ImagesSelectorView(imageSelected = imageSelected)
    BasicSpacer()
    GeneralButton(modifier, "CREATE THE SPOT", onClick = { creationButtonTapped() })
    BasicSpacer()
    GeneralButton(modifier, "Previous", onClick = { previousButtonTapped() }, style = GeneralButtonStyle.BACKGROUND)
    BasicSpacer()
    CustomPageIndicator(currentIndex = remember { mutableStateOf(currentIndex) },
        indexCount = numberOfPage)
}
