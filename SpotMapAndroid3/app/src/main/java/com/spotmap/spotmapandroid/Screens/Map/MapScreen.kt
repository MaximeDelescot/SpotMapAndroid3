package com.spotmap.spotmapandroid.Screens.Map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MapScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Top) {
        Button(onClick = { navController.navigate("spotDetails") }, modifier = modifier) {
            Text(text = "Aller aux détails du spot")
        }
    }
}