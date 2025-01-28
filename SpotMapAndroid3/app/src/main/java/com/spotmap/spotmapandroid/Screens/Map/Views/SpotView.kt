package com.spotmap.spotmapandroid.Screens.Map.Views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.LargeTitleText
import com.spotmap.spotmapandroid.Commons.SmallNormalText
import com.spotmap.spotmapandroid.Commons.TitleText
import com.spotmap.spotmapandroid.R
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.AddGeneralInformationView
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.AddImagesView
import com.spotmap.spotmapandroid.Screens.AddSpot.Views.AddLocationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun SpotView(modifier: Modifier = Modifier, spot: MutableState<Spot?>) {
    Box(
        modifier = modifier.fillMaxSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.SecondaryColor))) {

        Row(modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            SmallImagesView(urls = spot.value?.imageUrls ?: listOf())
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.fillMaxHeight().weight(1f)) {
                SmallNormalText(spot.value?.getType().toString().uppercase(), color = colorResource(id= R.color.LightColor))
                TitleText(spot.value?.name ?: "", color = colorResource(id= R.color.LightColor))
            }
            Spacer(Modifier.width(16.dp))
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "arrow next",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.PrimaryColor)) )
        }
    }
}

@Composable
fun SmallImagesView(urls: List<String>) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
            .fillMaxHeight()
            .aspectRatio(1f)
            .background(colorResource(id=R.color.BackgroundColor))
    ) {
        InfiniteCarousel(
            modifier = Modifier.fillMaxSize(),
            scrollTime = 3,
            imageUrls = urls)
    }
}

@Composable
private fun InfiniteCarousel(
    modifier: Modifier,
    imageUrls: List<String>,
    scrollTime: Int? = null,
    scrollEnable: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        val pageCount = imageUrls.size * 400
        val pagerState = rememberPagerState(
            initialPage = pageCount / 2,
            pageCount = { pageCount }
        )

        // Auto-scroll logic
        LaunchedEffect(scrollTime) {
            if (scrollTime != null && imageUrls.size > 1) {
                while (isActive) {
                    delay((scrollTime * 1000).toLong())
                    val nextPage = (pagerState.currentPage + 1) % pageCount
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = scrollEnable,
            state = pagerState,
            beyondViewportPageCount = 1
        ) { page ->
            Column(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    model = imageUrls[page % imageUrls.size],
                    contentDescription = null
                )
            }
        }
    }
}


