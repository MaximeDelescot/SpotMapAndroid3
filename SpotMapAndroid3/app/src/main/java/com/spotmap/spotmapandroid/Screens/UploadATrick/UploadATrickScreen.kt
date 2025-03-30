package com.spotmap.spotmapandroid.Screens.UploadATrick

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.data.Group
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.GeneralButtonStyle
import com.spotmap.spotmapandroid.Commons.LargeCustomTextField
import com.spotmap.spotmapandroid.Commons.SeparatorView
import com.spotmap.spotmapandroid.R
import com.spotmap.spotmapandroid.Screens.SpotDetails.SpotDetailsItem
import com.spotmap.spotmapandroid.Screens.SpotDetails.SpotDetailsScreenViewModel
import com.spotmap.spotmapandroid.Screens.SpotDetails.VideoPlayerScreen
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.CommentsView
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.PublicationView
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.SpotDetailsView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadATrickScreen(
    viewModel: UploadATrickScreenViewModel,
    navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Upload a trick") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = colorResource(id = R.color.LightColor),
                    navigationIconContentColor = colorResource(id = R.color.LightColor),
                    containerColor = colorResource(id = R.color.SecondaryColor).copy(alpha = 0.8f) // Applique la transparence
                )
            )
        },
        content = { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(colorResource(id = R.color.BackgroundColor))
                    .padding(innerPadding)
                    .padding(8.dp)
                    .fillMaxSize() // S'assure que la colonne prend toute la hauteur disponible
            ) {
                Box(
                    modifier = Modifier
                        .aspectRatio(3f / 4f) // Conserve le ratio
                        .fillMaxHeight() // Prend toute la hauteur possible dans Column
                        .weight(2f)
                ) {
                    VideoPlayerScreen(url = viewModel.videoUri.value.toString())
                }

                Column(modifier = Modifier.weight(3f)) {
                    BasicSpacer()
                    LargeCustomTextField(
                        color = colorResource(R.color.SecondaryColor),
                        placeholder = "Add a description",
                        onTextDidChange = {}
                    )
                    BasicSpacer()
                    GeneralButton(
                        title = "Share",
                        onClick = {},
                        isEnable = true,
                        style = GeneralButtonStyle.PRIMARY
                    )
                }
            }

        }
    )

}