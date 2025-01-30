package com.spotmap.spotmapandroid.Screens.Map.Views

import android.media.metrics.Event
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Class.SpotType
import com.spotmap.spotmapandroid.R
import java.util.UUID
import kotlin.uuid.Uuid


class ZoomTarget(val position: LatLng, val id: String = UUID.randomUUID().toString()) {}

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    spots: List<Spot>?,
    selectedSpot: MutableState<Spot?>,
    zoomPosition: MutableState<ZoomTarget?>
) {

    val cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(LatLng(48.866667, 2.333333), 10f) }

    LaunchedEffect(zoomPosition.value) {
        zoomPosition.value?.let { newPosition ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(newPosition.position, 12f),
                1000
            )
        }
    }

    var uiSettings = remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }

    GoogleMap(
        uiSettings = uiSettings.value,
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        googleMapOptionsFactory = { GoogleMapOptions().mapColorScheme(MapColorScheme.DARK) }
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
                    icon = icon,
                    onInfoWindowClose = {
                        selectedSpot.value = null
                    },
                    onClick = {
                        selectedSpot.value = spot
                        false
                    }
                )
            }
        }
    }

}