package com.spotmap.spotmapandroid.Commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@androidx.media3.common.util.UnstableApi
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoUrl: String,
    isPlaying: Boolean, // Propriété pour contrôler la lecture depuis l'extérieur
    onPlayStateChanged: (Boolean) -> Unit // Fonction de rappel pour notifier l'état de la lecture
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = isPlaying // Utiliser l'état extérieur pour démarrer/arrêter
        }
    }

    var isVideoEnded = remember { mutableStateOf(false) }

    // Ajouter un listener pour détecter la fin de la vidéo
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    isVideoEnded.value = true
                }
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener) // Nettoyer le listener
        }
    }

    // Observer l'état de lecture et mettre à jour l'exoPlayer
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            exoPlayer.playWhenReady = true
        } else {
            exoPlayer.playWhenReady = false
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape),
            factory = {
                PlayerView(it).apply {
                    useController = false
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            }
        )

        if (isVideoEnded.value) {
            Button(
                onClick = {
                    exoPlayer.seekTo(0)
                    exoPlayer.playWhenReady = true // Démarre la vidéo
                    isVideoEnded.value = false
                    onPlayStateChanged(true) // Notifie le parent pour qu'il sache que la vidéo est en cours
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.Black.copy(alpha = 0.7f), shape = CircleShape)
                    .padding(16.dp)
            ) {
                Text("Rejouer", color = Color.White, fontSize = 20.sp)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}
