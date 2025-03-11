package com.spotmap.spotmapandroid.Screens.SpotDetails.Views

import android.media.MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
import android.media.MediaPlayer
import android.net.Uri
import android.view.SurfaceView
import android.view.TextureView
import android.widget.MediaController
import android.widget.VideoView
import androidx.annotation.OptIn
import androidx.compose.foundation.AndroidExternalSurface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.spotmap.spotmapandroid.Class.Publication
import com.spotmap.spotmapandroid.Commons.NormalText
import com.spotmap.spotmapandroid.Commons.SmallNormalText
import com.spotmap.spotmapandroid.Commons.TitleButton
import com.spotmap.spotmapandroid.Commons.UserImageView
import com.spotmap.spotmapandroid.R
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.media3.ui.AspectRatioFrameLayout
import com.spotmap.spotmapandroid.Screens.SpotDetails.VideoPlayerScreen

@Composable
fun PublicationView(
    publication: Publication,
    nameClick: () -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.Top) {
            UserImageView(
                modifier = Modifier,
                url = publication.creator.photoUrl ?: "",
                height = 55.dp,
                onClick = { }
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TitleButton(title = publication.creator.userName, onClick = nameClick)
                    Spacer(Modifier.width(4.dp))
                    SmallNormalText(
                        publication.creationDate.timeSinceDate(),
                        color = colorResource(id = R.color.LightDarker1Color)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                SmallNormalText(publication.spot.name)
            }
        }

        // Affichage de la vidéo avec ExoPlayer
        publication.videoUrl?.let {
            Spacer(Modifier.height(8.dp))
            VideoPlayerScreen(url = it)
//            ExoPlayerVideoScreen(
//                "https://cdn.pixabay.com/video/2015/08/20/468-136808389_large.mp4".toUri(),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height((LocalConfiguration.current.screenWidthDp * (4f / 3f)).dp) // Hauteur dynamique selon le ratio
//            )
        }

        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier,
            horizontalArrangement = Arrangement.End) {
            IconWithValueView(
                iconId=R.drawable.ic_like,
                value = 12)

            Spacer(modifier = Modifier.width(8.dp))
            IconWithValueView(
                iconId=R.drawable.ic_comment,
                value = 12)
        }

        publication.description?.let {
            if (publication.description.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                NormalText(it, color = colorResource(id = R.color.LightColor))
            }
        }
    }
}

//@OptIn(UnstableApi::class)
//@Composable
//fun ExoPlayerVideoScreen(videoUri: Uri, modifier: Modifier = Modifier) {
//    val context = LocalContext.current
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            val mediaItem = MediaItem.Builder()
//                .setUri(videoUri)
//                .build()
//            setMediaItem(mediaItem)
//            prepare()
//            playWhenReady = true
//        }
//    }
//
//    DisposableEffect(exoPlayer) {
//        onDispose { exoPlayer.release() }
//    }
//
//    Box(
//        modifier = modifier.fillMaxSize().background(Color.Black)
//    ) {
//        AndroidView(
//            factory = { ctx ->
//                SurfaceView(context).also {
//                    exoPlayer.setVideoSurfaceView(it)
//                    exoPlayer.setVideoScalingMode(VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
//                }
//            },
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}

private const val VIDEO_URL = "https://cdn.pixabay.com/video/2015/08/20/468-136808389_large.mp4"
