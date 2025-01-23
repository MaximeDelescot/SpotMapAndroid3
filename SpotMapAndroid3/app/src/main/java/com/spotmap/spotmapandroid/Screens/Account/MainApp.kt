package com.spotmap.spotmapandroid.Screens.Account

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spotmap.spotmapandroid.BottomBar
import com.spotmap.spotmapandroid.Screens.AddSpot.AddSpotScreen
import com.spotmap.spotmapandroid.Screens.Map.MapScreen
import com.spotmap.spotmapandroid.Screens.Social.SocialScreen
import com.spotmap.spotmapandroid.Screens.SpotDetails.SpotDetailsScreen
import com.spotmap.spotmapandroid.Screens.UserDetails.UserDetailsScreen
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.UserHandler

@Composable
fun MainApp() {
    val test = "sdqdqshqshdqsdjjqsd"
    val navController = rememberNavController()
    val apiService = APIService()
    val userHandler = UserHandler(apiService)

    val accountViewModel = AccountScreenViewModel(userHandler)

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "map",
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            composable("map") {
                MapScreen(navController = navController)
            }
            composable("social") {
                SocialScreen()
            }
            composable("addSpot") {
                AddSpotScreen()
            }
            composable("account") {
                AccountScreen(
                    navController = navController,
                    viewModel = accountViewModel)
            }
            composable("spotDetails") {
                SpotDetailsScreen(navController = navController)
            }
            composable("userDetails") {
                UserDetailsScreen(navController = navController)
            }
        }
    }
}