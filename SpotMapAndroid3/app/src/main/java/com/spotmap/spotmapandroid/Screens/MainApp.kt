package com.spotmap.spotmapandroid.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.UserHandler

@Composable
fun MainApp() {
    val test = "sdqdqsd"
    val navController = rememberNavController()
    val apiService = APIService()
    val userHandler = UserHandler(apiService)
//
//    val accountViewModel = AccountScreenViewModel(userHandler)
//
//    Scaffold(
//        bottomBar = { BottomBar(navController = navController) }
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = "map",
//            modifier = Modifier.padding(innerPadding).fillMaxSize()
//        ) {
//            composable("map") {
//                MapScreen(navController = navController)
//            }
//            composable("social") {
//                SocialScreen()
//            }
//            composable("addSpot") {
//                AddSpotScreen()
//            }
//            composable("account") {
//                AccountScreen(
//                    navController = navController,
//                    viewModel = accountViewModel)
//            }
//            composable("spotDetails") {
//                SpotDetailsScreen(navController = navController)
//            }
//            composable("userDetails") {
//                UserDetailsScreen(navController = navController)
//            }
//        }
//    }
}