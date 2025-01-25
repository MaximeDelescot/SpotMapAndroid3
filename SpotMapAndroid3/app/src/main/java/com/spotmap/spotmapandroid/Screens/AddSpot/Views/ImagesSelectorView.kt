package com.spotmap.spotmapandroid.Screens.AddSpot.Views

import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.spotmap.spotmapandroid.R
import kotlin.collections.List
import kotlin.collections.plus

enum class ImageSelectorItemType { PLUS, IMAGE }

class ImageSelectorItem(
    val id: Int,
    val type: ImageSelectorItemType) {
}

@Composable
fun ImagesSelectorView() {
    val numberMaxOfItem = 4
    val newImageSelected = remember { mutableStateOf<List<Uri?>?>(null) }
    val imageSelected = remember { mutableStateListOf<Uri>() }

    LaunchedEffect(newImageSelected.value) {
        newImageSelected.value?.filterNotNull()?.forEach { uri ->
            if (!imageSelected.contains(uri) && imageSelected.size < numberMaxOfItem) {
                imageSelected.add(uri)
            }
        }
    }

    val imagesSelected by remember(imageSelected) {
        derivedStateOf { imageSelected.take(numberMaxOfItem) }
    }

    val items by remember(imagesSelected) {
        derivedStateOf {
            val itemList = mutableListOf<ImageSelectorItem>()
            imagesSelected.forEachIndexed { index, _ ->
                itemList.add(ImageSelectorItem(type = ImageSelectorItemType.IMAGE, id = index))
            }
            if (imagesSelected.size < numberMaxOfItem) {
                itemList.add(ImageSelectorItem(type = ImageSelectorItemType.PLUS, id = imagesSelected.size))
            }
            itemList
        }
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

        items(items.size) { index ->
            when (items[index].type) {
                ImageSelectorItemType.PLUS -> AddImageItem(modifier, newImageSelected)
                ImageSelectorItemType.IMAGE -> ImageItem(
                    modifier,
                    uri = imagesSelected[index],
                    closeButtonTapped = {
                        imageSelected.remove(imagesSelected[index]) // Remove the image when close is tapped
                    }
                )
            }
        }
    }
}




@Composable
fun AddImageItem(modifier: Modifier, imagesSelected: MutableState<List<Uri?>?>) {

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { imagesSelected.value = it }

    Box(modifier = modifier.background(color = colorResource(id=R.color.BackgroundColor))) {
        TextButton(onClick = {
            launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) {
            Image(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "logo app",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.LightColor)) )
        }
    }
}

@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    uri: Uri? = null,
    closeButtonTapped: () -> Unit = {}
) {
    Box(modifier = modifier.background(color = colorResource(id = R.color.BackgroundColor))) {

        AsyncImage(
            model = uri.toString(),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
        )

        CancelButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 12.dp, y = (-8).dp),
            onButtonTapped = closeButtonTapped
        )
    }
}


@Composable
fun CancelButton(modifier: Modifier = Modifier, onButtonTapped: () -> Unit) {
    Box(modifier = modifier) {
        TextButton(
            modifier = Modifier.padding(0.dp), // Supprime tout padding autour du bouton
            onClick = onButtonTapped
        ) {
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .background(color = Color.Black, shape = CircleShape),
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "remove image",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.PrimaryColor))
            )
        }
    }
}


@Preview(showBackground = false)
@Composable
fun PreviewImageItem() {
    ImageItem()
}