package com.spotmap.spotmapandroid

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.spotmap.spotmapandroid.Screens.Account.AccountScreenViewModel

@Composable
fun BottomBar(navController: NavController) {

    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route

    val itemSelectedColor = colorResource(id = R.color.LightColor)
    val itemUnselectedColor = colorResource(id = R.color.LightDarker2Color)

    NavigationBar(
        containerColor = colorResource(id = R.color.SecondaryColor)
    ) {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_earth), contentDescription = "Map") },
            label = { Text("Map") },
            selected = currentRoute == "map",
            onClick = {
                navController.navigate("map") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = itemSelectedColor,
                selectedTextColor = itemSelectedColor,
                unselectedIconColor = itemUnselectedColor,
                unselectedTextColor = itemUnselectedColor,
                indicatorColor = androidx.compose.ui.graphics.Color.Transparent // Suppression de la "bulle"
            )
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_social), contentDescription = "Social") },
            label = { Text("Social") },
            selected = currentRoute == "social",
            onClick = {
                navController.navigate("social") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = itemSelectedColor,
                selectedTextColor = itemSelectedColor,
                unselectedIconColor = itemUnselectedColor,
                unselectedTextColor = itemUnselectedColor,
                indicatorColor = androidx.compose.ui.graphics.Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_add_spot), contentDescription = "Add spot") },
            label = { Text("Add spot") },
            selected = currentRoute == "addSpot",
            onClick = {
                navController.navigate("addSpot") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = itemSelectedColor,
                selectedTextColor = itemSelectedColor,
                unselectedIconColor = itemUnselectedColor,
                unselectedTextColor = itemUnselectedColor,
                indicatorColor = androidx.compose.ui.graphics.Color.Transparent
            )
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_user), contentDescription = "Account") },
            label = { Text("Account") },
            selected = currentRoute == "account",
            onClick = {
                navController.navigate("account") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = itemSelectedColor,
                selectedTextColor = itemSelectedColor,
                unselectedIconColor = itemUnselectedColor,
                unselectedTextColor = itemUnselectedColor,
                indicatorColor = androidx.compose.ui.graphics.Color.Transparent
            )
        )
    }
}