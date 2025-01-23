package com.spotmap.spotmapandroid.Screens.Social

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun SocialScreen(modifier: Modifier = Modifier) {
    // Contenu de l'écran "Home"
    Text(
        text = "Bienvenue dans SOCIAL",
        style = TextStyle(fontSize = 24.sp),
        modifier = modifier
    )
}