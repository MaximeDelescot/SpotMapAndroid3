package com.spotmap.spotmapandroid.Screens.SpotDetails

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.storage.FirebaseStorage
import com.spotmap.spotmapandroid.Class.Comment
import com.spotmap.spotmapandroid.Class.LoadableResource
import com.spotmap.spotmapandroid.Class.Publication
import com.spotmap.spotmapandroid.Class.Skater
import com.spotmap.spotmapandroid.Class.SkaterLight
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Class.SpotLight
import com.spotmap.spotmapandroid.Commons.CustomPageIndicator
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.GeneralButtonStyle
import com.spotmap.spotmapandroid.Commons.LargeTitleText
import com.spotmap.spotmapandroid.Commons.NormalButton
import com.spotmap.spotmapandroid.Commons.NormalText
import com.spotmap.spotmapandroid.Commons.SeparatorView
import com.spotmap.spotmapandroid.Commons.SmallNormalText
import com.spotmap.spotmapandroid.Commons.TitleButton
import com.spotmap.spotmapandroid.Commons.TitleText
import com.spotmap.spotmapandroid.Commons.UserImageView
import com.spotmap.spotmapandroid.Commons.Utils.convertToFastestUrl
import com.spotmap.spotmapandroid.Screens.Map.Views.InfiniteCarousel
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.CommentsView
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.PublicationView
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.SpotDetailsView
import com.spotmap.spotmapandroid.Screens.SpotDetails.Views.timeSinceDate
import com.spotmap.spotmapandroid.Screens.UserDetails.UserDetailsScreenViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.nio.file.WatchEvent
import java.sql.Date


@OptIn(ExperimentalMaterial3Api::class)
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

//                            val isPlaying = remember { mutableStateOf(playingState.value[index] ?: false) }
//
//                            val exoPlayer = remember {
//                                ExoPlayer.Builder(context).build().apply {
//                                    setMediaItem(MediaItem.fromUri(item.publication.videoUrl.toString()))
//                                    prepare()
//                                }
//                            }
//
//                            LaunchedEffect(playingState.value[index]) {
//                                Log.d("SpotDetailsScreen", "Publication isPlaying[$index]: ${isPlaying.value}")
//                                isPlaying.value = playingState.value[index] ?: false
//                            }
//
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
