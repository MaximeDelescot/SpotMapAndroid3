package com.spotmap.spotmapandroid.Screens.SpotDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.livedata.observeAsState
import com.spotmap.spotmapandroid.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Commons.CustomPageIndicator
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.GeneralButtonStyle
import com.spotmap.spotmapandroid.Commons.LargeTitleText
import com.spotmap.spotmapandroid.Commons.NormalText
import com.spotmap.spotmapandroid.Commons.SmallNormalText
import com.spotmap.spotmapandroid.Screens.Map.Views.InfiniteCarousel
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.SpotDetailsView
import java.nio.file.WatchEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotDetailsScreen(navController: NavController,
                      modifier: Modifier = Modifier,
                      viewModel: SpotDetailsScreenViewModel) {

    val spot = viewModel.spot.observeAsState(null)
    val items = viewModel.items.observeAsState(listOf())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Spot Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = colorResource(id = R.color.LightColor),
                    navigationIconContentColor = colorResource(id = R.color.LightColor),
                    containerColor = colorResource(id = R.color.SecondaryColor).copy(alpha = 0.8f) // Applique la transparence
                )
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.BackgroundColor))) {
                items(items.value) { item ->
                    when (item) {
                        SpotDetailsItem.SPOTDETAILS ->
                            spot.value?.let { SpotDetailsView(it) }
                        SpotDetailsItem.COMMENTS ->
                            Text(text = "COMMENTS")
                    }
                }
            }
        }
    )
}