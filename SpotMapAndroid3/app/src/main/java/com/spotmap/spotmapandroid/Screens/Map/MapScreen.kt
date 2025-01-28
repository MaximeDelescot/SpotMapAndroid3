package com.spotmap.spotmapandroid.Screens.Map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Screens.Map.Views.MapView
import com.spotmap.spotmapandroid.Screens.Map.Views.SpotView


@Composable
fun MapScreen(navController: NavController,
              modifier: Modifier = Modifier,
              viewModel: MapScreenViewModel) {

    val spots = viewModel.spots.observeAsState().value
    viewModel.loadSpots()

    val spotSelected: MutableState<Spot?> = remember { mutableStateOf(null) }

    Box(modifier = modifier.fillMaxSize()) {

        MapView(
            modifier = modifier,
            spots = spots,
            selectedSpot = spotSelected
        )

        if (spotSelected.value != null) {
            SpotView(
                spot = spotSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}