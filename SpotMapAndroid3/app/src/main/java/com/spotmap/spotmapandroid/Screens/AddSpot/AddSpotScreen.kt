package com.spotmap.spotmapandroid.Screens.AddSpot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.R
import com.spotmap.spotmapandroid.Screens.Account.AccountScreenViewModel
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.AddGeneralInformationView
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.AddImagesView
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.AddLocationView
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.UserHandler
import kotlinx.coroutines.launch


@Composable
fun AddSpotScreen(modifier: Modifier = Modifier, viewModel: AddSpotScreenViewModel) {

    val numberOfPages = 3
    val currentPage = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = currentPage.value, pageCount = { numberOfPages })

    var nameText = remember { mutableStateOf("") }
    var descriptionText = remember { mutableStateOf("") }

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
            if (currentPage.value > 0) { // Assurez-vous de ne pas descendre en dessous de 0
                currentPage.value = currentPage.value - 1
                pagerState.animateScrollToPage(currentPage.value)
            }
        }
    }

    fun createSpot() {
        viewModel.createSpot(
            name = nameText.value,
            description= descriptionText.value)
    }

    Column(modifier = modifier.background(color = colorResource(id = R.color.BackgroundColor))) {

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
                            creationButtonTapped = { createSpot() }
                        )
                    }
                }
            }
        }

        Spacer(modifier = modifier.weight(1f))
    }
}


@Preview(showBackground = false)
@Composable
fun previewSpotScren() {

    val apiService = APIService()
    val userHandler = UserHandler(apiService)

    AddSpotScreen(modifier = Modifier, viewModel = AddSpotScreenViewModel(userHandler, apiService))
}
