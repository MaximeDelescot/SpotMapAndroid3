package com.spotmap.spotmapandroid.Screens.AddSpot.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.R
import kotlin.collections.plus


enum class ImageSelectorItemType { PLUS, IMAGE }

class ImageSelectorItem(
    val id: Int,
    val type: ImageSelectorItemType) {
}

@Composable
fun ImagesSelectorView() {

    val items: MutableState<List<ImageSelectorItem>> = remember {
        mutableStateOf(listOf(ImageSelectorItem(
            type = ImageSelectorItemType.PLUS,
            id = 0)))
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier,
        contentPadding = PaddingValues(0.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)

        items(items.value.size) { index ->

            when(items.value[index].type) {
                ImageSelectorItemType.PLUS ->
                    AddImageItem(modifier, onClick = {
                        items.value = listOf(ImageSelectorItem(type = ImageSelectorItemType.IMAGE, id = items.value.size)) + items.value
                    })
                ImageSelectorItemType.IMAGE ->
                    ImageItem(modifier)
            }

        }
    }
}

@Composable
fun AddImageItem(modifier: Modifier, onClick: () -> Unit = {}) {
    Box(modifier = modifier.background(color = colorResource(id=R.color.BackgroundColor))) {

        TextButton(onClick = onClick) {
            Image(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "logo app",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.LightColor)) )
        }
    }
}

@Composable
fun ImageItem(modifier: Modifier) {
    Box(modifier = modifier.background(color = colorResource(id=R.color.BackgroundColor))) {
        Image(
            modifier = Modifier.fillMaxWidth().aspectRatio(1f),
            painter = painterResource(id = R.drawable.ic_earth),
            contentDescription = "logo app",
            colorFilter = ColorFilter.tint(colorResource(id = R.color.LightColor)))
    }
}