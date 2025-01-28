package com.spotmap.spotmapandroid.Screens.Map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.UiSettings
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.IconBorderButton
import com.spotmap.spotmapandroid.Screens.Map.Views.MapView
import com.spotmap.spotmapandroid.Screens.Map.Views.SpotView
import com.spotmap.spotmapandroid.R

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

        val isVisible = spotSelected.value != null

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.End
        ) {

            IconBorderButton(iconResId = R.drawable.ic_location) { }
            Spacer(modifier.height(4.dp))

            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 500)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 500)
                )
            ) {
                spotSelected.value?.let { spot ->
                    SpotView(
                        modifier = Modifier,
                        spot = spotSelected
                    )
                }
            }
        }

    }
}