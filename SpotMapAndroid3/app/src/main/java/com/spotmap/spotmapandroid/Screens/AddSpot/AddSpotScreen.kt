package com.spotmap.spotmapandroid.Screens.AddSpot

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.Class.SpotType
import com.spotmap.spotmapandroid.R
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.AddGeneralInformationView
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.AddImagesView
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.AddLocationView
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.NoLoggedView
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

@Composable
fun AddSpotScreen(modifier: Modifier = Modifier,
                  viewModel: AddSpotScreenViewModel) {

    val context = LocalContext.current

    val numberOfPages = 3
    val currentPage = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = currentPage.value, pageCount = { numberOfPages })

    val displayedView = viewModel.viewToDisplay.observeAsState(AddSpotScreenViewModel.AddSpotScreenViewType.NOTLOGGED)

    var nameText = remember { mutableStateOf("") }
    var descriptionText = remember { mutableStateOf("") }
    val imageSelected = remember { mutableStateListOf<Uri>() }
    val typeIndex = remember { mutableStateOf(0) }
    val typeItems = listOf<SpotType>(SpotType.Street, SpotType.Park)

    fun goToNext() {
        coroutineScope.launch {
            if (currentPage.value < numberOfPages - 1) {
                currentPage.value = currentPage.value + 1
                pagerState.animateScrollToPage(currentPage.value)
            }
        }
    }

    fun goToPrevious() {
        coroutineScope.launch {
            if (currentPage.value > 0) {
                currentPage.value = currentPage.value - 1
                pagerState.animateScrollToPage(currentPage.value)
            }
        }
    }

    fun createSpot() {
        val selectedImages = imageSelected.map { uri ->
            val imageView = ImageView(context)
            imageView.setImageURI(uri)
            imageView
        }

        viewModel.createSpot(
            name = nameText.value,
            description= descriptionText.value,
            type = typeItems[typeIndex.value],
            selectedImage = selectedImages)
    }

    Column(
        modifier = modifier.background(color = colorResource(id = R.color.BackgroundColor)),
        horizontalAlignment = Alignment.CenterHorizontally) {

        when (displayedView.value) {

            AddSpotScreenViewModel.AddSpotScreenViewType.SUCCED -> {
                Text("SUCCED")
            }
            AddSpotScreenViewModel.AddSpotScreenViewType.NOTLOGGED -> {
                NoLoggedView()
            }
            AddSpotScreenViewModel.AddSpotScreenViewType.LOADING -> {
                Column(
                    modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = colorResource(id= R.color.PrimaryColor), modifier = modifier)
                }
            }

            else -> {
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                ) { page ->
                    Column(
                        modifier = modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = modifier
                                .padding(16.dp)
                                .background(
                                    color = colorResource(id = R.color.SecondaryColor),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 32.dp)
                        ) {
                            when (page) {
                                0 -> AddGeneralInformationView(
                                    modifier,
                                    nameText = nameText,
                                    descriptionText = descriptionText,
                                    currentIndex = 0,
                                    numberOfPage = numberOfPages,
                                    typeIndex = typeIndex,
                                    typeItems = typeItems.map{ it.name },
                                    nextButtonTapped = { goToNext() }
                                )
                                1 -> AddLocationView(
                                    modifier,
                                    currentIndex = 1,
                                    numberOfPage = numberOfPages,
                                    nextButtonTapped = { goToNext() },
                                    previousButtonTapped = { goToPrevious() }
                                )
                                2 -> AddImagesView(
                                    modifier,
                                    currentIndex = 2,
                                    numberOfPage = numberOfPages,
                                    previousButtonTapped = { goToPrevious() },
                                    creationButtonTapped = { createSpot() },
                                    imageSelected = imageSelected)
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = modifier.weight(1f))
    }
}


