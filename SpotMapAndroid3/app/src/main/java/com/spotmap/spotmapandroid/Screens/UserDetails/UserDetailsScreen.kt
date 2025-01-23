package com.spotmap.spotmapandroid.Screens.UserDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.UserHandler

@Composable
fun UserDetailsScreen(navController: NavController,
                      modifier: Modifier = Modifier) {
    // Scaffold avec TopAppBar de Material2
    Scaffold(
        content = { innerPadding ->
            Column(verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "USER DETAILS",
                    modifier = modifier.padding(innerPadding)
                )
                Button(
                    onClick = {
                        val apiService = APIService()
                        UserHandler(apiService).logOut()}
                ) {
                    Text("Log out")
                }
            }
        }
    )
}