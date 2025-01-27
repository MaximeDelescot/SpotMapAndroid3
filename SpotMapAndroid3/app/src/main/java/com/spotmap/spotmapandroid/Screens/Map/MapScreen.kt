package com.spotmap.spotmapandroid.Screens.Map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.spotmap.spotmapandroid.Class.Coordinate
import com.spotmap.spotmapandroid.Class.SkaterLight
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Class.SpotType
import com.spotmap.spotmapandroid.Screens.AddSpot.AddSpotScreenViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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
            modifier= modifier,
            spots = spots,
            selectedSpot = spotSelected)

        if (spotSelected.value != null) {
            SpotView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun SpotView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
        .padding(8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(colorResource(id = R.color.SecondaryColor))) {

        Row(modifier = modifier.fillMaxSize().padding(16.dp)) {
            Column(modifier = modifier.weight(1f)) {
                Text("PARK", color = colorResource(id= R.color.LightColor))
                Text("qsdqsd", color = colorResource(id= R.color.LightColor))
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun previewSpotView() {
    SpotView()
}