package com.spotmap.spotmapandroid.Commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.R

@Composable
fun SeparatorView() {
    Box(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp).fillMaxWidth().height(1.dp).background(colorResource(id= R.color.LightDarker2Color)))
}