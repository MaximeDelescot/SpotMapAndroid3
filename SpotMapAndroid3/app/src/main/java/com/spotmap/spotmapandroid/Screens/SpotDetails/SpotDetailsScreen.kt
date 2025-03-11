package com.spotmap.spotmapandroid.Screens.SpotDetails

import android.util.Log
import androidx.compose.foundation.AndroidExternalSurface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.livedata.observeAsState
import com.spotmap.spotmapandroid.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.spotmap.spotmapandroid.Commons.SeparatorView
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.CommentsView
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.PublicationView
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.SpotDetailsView

import com.spotmap.spotmapandroid.Screens.UserDetails.UserDetailsScreenViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun SpotDetailsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SpotDetailsScreenViewModel,
    userDetailsScreenViewModel: UserDetailsScreenViewModel
) {
    val items = viewModel.items.observeAsState(listOf())
    val listState = rememberLazyListState()

    val playingState = remember { mutableStateOf<Map<Int, Boolean>>(emptyMap()) }

    fun getCenteredItemIndex(): Int? {
        val firstVisible = listState.firstVisibleItemIndex
        val offset = listState.firstVisibleItemScrollOffset
        return if (offset > 0) firstVisible + 1 else firstVisible
    }

    LaunchedEffect(listState) {
        snapshotFlow { getCenteredItemIndex() }
            .distinctUntilChanged()
            .collect { centeredIndex ->
                playingState.value = playingState.value.toMutableMap().apply {
                    keys.forEach { put(it, false) }
                    if (centeredIndex != null) put(centeredIndex, true)
                }
            }
    }

    val context = LocalContext.current

    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Spot Details") },
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
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().background(colorResource(id = R.color.BackgroundColor))
            ) {
                itemsIndexed(items.value) { index, item ->
                    when (item) {
                        is SpotDetailsItem.publication -> {

                            Column(Modifier.fillMaxSize()) {

                                PublicationView(
                                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                                    publication = item.publication,
                                    nameClick = {}
                                )
                                SeparatorView()
                            }
                        }
                        is SpotDetailsItem.SpotDetails -> {
                            SpotDetailsView(modifier = Modifier.padding(bottom = 8.dp), spot = item.spot)
                            SeparatorView()
                        }
                        is SpotDetailsItem.loading -> {
                            Row(
                                modifier = modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(color = colorResource(id = R.color.PrimaryColor))
                            }
                        }
                        is SpotDetailsItem.Comments -> {
                            CommentsView(
                                comments = item.comments,
                                commentsCount = item.spot.commentCount,
                                viewModel = viewModel,
                                skaterNameClick = { skater ->
                                    userDetailsScreenViewModel.updateSkaterId(skater.id)
                                    navController.navigate("userDetails")
                                }
                            )
                            SeparatorView()
                        }
                    }
                }
            }
        }
    )
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(url: String) {
    val context = LocalContext.current
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            playWhenReady = true
        }
    }

    LaunchedEffect(url) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
    }

    DisposableEffect(url) {
        onDispose {
            player.pause()
            player.release()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxWidth()
            .height((LocalConfiguration.current.screenWidthDp * (4f / 3f)).dp).clipToBounds(),
        factory = { context ->
            PlayerView(context).apply {
                this.player = player
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        }
    )
}