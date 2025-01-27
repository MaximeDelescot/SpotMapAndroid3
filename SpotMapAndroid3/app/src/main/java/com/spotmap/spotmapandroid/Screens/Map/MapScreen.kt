package com.spotmap.spotmapandroid.Screens.Map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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

    val paris = LatLng(48.866667, 2.333333)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(paris, 10f)
    }

    val spots = viewModel.spots.observeAsState().value
    viewModel.loadSpots()

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        googleMapOptionsFactory = { GoogleMapOptions().mapColorScheme(MapColorScheme.DARK) },
        ) {



        if (spots != null) {
            for (spot in spots) {

                val icon = if (spot.getType() == SpotType.Park) {
                    BitmapDescriptorFactory.fromResource(R.drawable.marker_1)
                } else {
                    BitmapDescriptorFactory.fromResource(R.drawable.marker_2)
                }

                Marker(
                    state = MarkerState(position = spot.coordinate.getLatLong()),
                    title = spot.name,
                    snippet = null,
                    icon = icon
                )
            }
        }
    }

}