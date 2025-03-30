package com.spotmap.spotmapandroid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spotmap.spotmapandroid.Screens.Account.AccountScreen
import com.spotmap.spotmapandroid.Screens.Account.AccountScreenViewModel
import com.spotmap.spotmapandroid.Screens.AddSpot.AddSpotScreen
import com.spotmap.spotmapandroid.Screens.AddSpot.AddSpotScreenViewModel
import com.spotmap.spotmapandroid.Screens.Map.MapScreen
import com.spotmap.spotmapandroid.Screens.Map.MapScreenViewModel
import com.spotmap.spotmapandroid.Screens.Social.SocialScreen
import com.spotmap.spotmapandroid.Screens.SpotDetails.SpotDetailsItem
import com.spotmap.spotmapandroid.Screens.SpotDetails.SpotDetailsScreen
import com.spotmap.spotmapandroid.Screens.SpotDetails.SpotDetailsScreenViewModel
import com.spotmap.spotmapandroid.Screens.UploadATrick.UploadATrickScreen
import com.spotmap.spotmapandroid.Screens.UploadATrick.UploadATrickScreenViewModel
import com.spotmap.spotmapandroid.Screens.UserDetails.UserDetailsScreen
import com.spotmap.spotmapandroid.Screens.UserDetails.UserDetailsScreenViewModel
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.StorageService
import com.spotmap.spotmapandroid.Services.UserHandler

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val apiService = APIService()
    val userHandler = UserHandler(apiService)
    val storageService = StorageService()

    val accountViewModel = AccountScreenViewModel(userHandler)
    val addSpotViewModel = AddSpotScreenViewModel(userHandler, apiService, storageService)
    val mapViewModel = MapScreenViewModel(apiService)
    val spotDetailsModel = SpotDetailsScreenViewModel(apiService, userHandler)
    val uploadATrickScreenViewModel = UploadATrickScreenViewModel(apiService, userHandler)

    val userDetailsModel = UserDetailsScreenViewModel(
        userHandler,
        storageService,
        apiService)

    Scaffold(
        bottomBar = { BottomBar(
            navController = navController,
            userHandler = userHandler) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "map",
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable("map") {
                MapScreen(
                    navController = navController,
                    viewModel = mapViewModel,
                    spotDetailsScreenViewModel = spotDetailsModel)
            }
            composable("social") {
                SocialScreen()
            }
            composable("addSpot") {
                AddSpotScreen(
                    navController= navController,
                    viewModel = addSpotViewModel,
                    spotDetailsScreenViewModel = spotDetailsModel)
            }
            composable("account") {
                AccountScreen(
                    navController = navController,
                    viewModel = accountViewModel)
            }
            composable("spotDetails") {
                SpotDetailsScreen(
                    navController = navController,
                    viewModel = spotDetailsModel,
                    userDetailsScreenViewModel = userDetailsModel,
                    uploadTrickViewModel = uploadATrickScreenViewModel)
            }
            composable("userDetails") {
                UserDetailsScreen(
                    navController = navController,
                    viewModel = userDetailsModel)
            }
            composable("uploadATrick") {
                UploadATrickScreen(
                    viewModel = uploadATrickScreenViewModel,
                    navController = navController)
            }
        }
    }
}