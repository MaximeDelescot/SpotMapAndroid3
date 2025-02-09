package com.spotmap.spotmapandroid.Screens.UserDetails

import android.util.Log
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spotmap.spotmapandroid.Class.Skater
import com.spotmap.spotmapandroid.Commons.CustomTopAppBar
import com.spotmap.spotmapandroid.Commons.SeparatorView
import com.spotmap.spotmapandroid.Commons.TopBarStyle
import com.spotmap.spotmapandroid.R
import com.spotmap.spotmapandroid.Screens.SpotDetails.SpotDetailsItem
import com.spotmap.spotmapandroid.Screens.SpotDetails.SpotDetailsScreenViewModel
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.SpotDetailsView
import com.spotmap.spotmapandroid.Screens.UserDetails.Views.UserDetailsView
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.UserHandler

@Composable
fun UserDetailsScreen(navController: NavController,
                      modifier: Modifier = Modifier,
                      viewModel: UserDetailsScreenViewModel) {

    val context = LocalContext.current

    val items = viewModel.items.observeAsState(listOf())
    val user = viewModel.user.observeAsState(null)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            val imageView = ImageView(context)
            imageView.setImageURI(uri)
            viewModel.saveUserImage(imageView = imageView)
        }
    }

    Scaffold(
        topBar = {
            if (navController?.previousBackStackEntry != null) {
                CustomTopAppBar(
                    title = "User Detail",
                    navController = navController,
                    style = TopBarStyle.BASIC,
                    showBackButton = true)
            }
        },
         content = { innerPadding ->
            LazyColumn(
                contentPadding = if(navController?.previousBackStackEntry != null) { innerPadding } else { PaddingValues(0.dp) },
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.BackgroundColor))) {
                items(items.value) { item ->
                    when (item) {
                        UserDetailsItem.USERDETAILS ->
                            user.value?.resource?.let { UserDetailsView(
                                user = it,
                                settingClick = { viewModel.userHandler.logOut() },
                                isCurrentUserPage = viewModel.isCurrentUserPage,
                                editClick = {launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) }
                        UserDetailsItem.LOADING ->
                            Row(modifier = modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.Center) {
                                CircularProgressIndicator(color = colorResource(id= R.color.PrimaryColor))

                            }
                        UserDetailsItem.SEPARATOR -> {
                            SeparatorView()
                        }
                    }
                }
            }
        }
    )
}

