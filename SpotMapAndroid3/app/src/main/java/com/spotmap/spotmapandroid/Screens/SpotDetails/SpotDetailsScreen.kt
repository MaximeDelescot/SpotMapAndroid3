package com.spotmap.spotmapandroid.Screens.SpotDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.spotmap.spotmapandroid.Screens.Account.AccountScreenViewModel
import com.spotmap.spotmapandroid.Screens.Map.MapScreenViewModel
import com.spotmap.spotmapandroid.Screens.Map.Views.InfiniteCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotDetailsScreen(navController: NavController,
                      modifier: Modifier = Modifier,
                      viewModel: SpotDetailsScreenViewModel) {

    val spot = viewModel.selectedSpot.observeAsState(null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Spot Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(innerPadding)) {
                

//                val spot = spot.value
//                if (spot != null) {
//                    val currentIndex = remember { mutableStateOf(0) }
//                    InfiniteCarousel(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .aspectRatio(4 / 3f),
//                        scrollTime = 3,
//                        imageUrls = remember { mutableStateOf(spot.imageUrls) },
//                        currentIndex = currentIndex)
//
//                    Text(spot.name)
//                }
            }
        }
    )
}