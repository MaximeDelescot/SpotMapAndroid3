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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
    // État pour les images sélectionnées
    val imagesSelected = remember { mutableStateOf<List<Uri?>?>(null) }


    // Fonction pour générer les éléments de la grille
    fun getItems(): List<ImageSelectorItem> {
        val items = mutableListOf<ImageSelectorItem>()
        imagesSelected.value?.forEachIndexed { index, _ ->
            items.add(ImageSelectorItem(type = ImageSelectorItemType.IMAGE, id = index - 1))
        }
        val size = imagesSelected.value?.size
        val plusIndex = if (size != null) size else 0
        items.add(ImageSelectorItem(type = ImageSelectorItemType.PLUS, id = plusIndex))
        return items
    }

    val items = remember(imagesSelected.value) { getItems() }

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
                ImageSelectorItemType.PLUS -> AddImageItem(modifier, imagesSelected)
                ImageSelectorItemType.IMAGE ->
                     ImageItem(modifier,
                         uri= ((imagesSelected.value ?: emptyList()) + null)[index],
                         closeButtonTapped = {})
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
fun ImageItem(modifier: Modifier = Modifier, uri: Uri? = null, closeButtonTapped: ()->Unit = {} ) {
    Box(modifier = modifier.background(color = colorResource(id = R.color.BackgroundColor))) {

        AsyncImage(
            model = uri.toString(),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth().aspectRatio(1f),
            contentScale = ContentScale.Crop,
        )

        CancelButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onButtonTapped = closeButtonTapped)
    }
}

@Composable
fun CancelButton(modifier: Modifier = Modifier, onButtonTapped: ()->Unit ) {
    Box(modifier = modifier) {
        TextButton(
            modifier = Modifier.padding(0.dp),
            onClick = onButtonTapped
        ) {
            Image(
                modifier = Modifier.size(30.dp).background(color = Color.Black, shape = CircleShape),
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