package com.spotmap.spotmapandroid.Commons

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.spotmap.spotmapandroid.R

enum class TopBarStyle {
    TRANSPARENT, BASIC
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    style: TopBarStyle = TopBarStyle.BASIC,
    navController: NavController? = null,
    showBackButton: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {}
) {
     val bgColor = when(style) {
            TopBarStyle.BASIC -> colorResource(id = R.color.SecondaryColor)
            TopBarStyle.TRANSPARENT -> colorResource(id = R.color.SecondaryColor).copy(alpha = 0.8f)
        }

        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                if (showBackButton && navController != null) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            },
            actions = actions,
            colors = TopAppBarDefaults.topAppBarColors(
                titleContentColor = colorResource(id = R.color.LightColor),
                navigationIconContentColor = colorResource(id = R.color.LightColor),
                containerColor = bgColor
            )
        )
}