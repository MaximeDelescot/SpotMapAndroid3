package com.spotmap.spotmapandroid.Screens.AddSpot.Views
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.CustomPageIndicator
import com.spotmap.spotmapandroid.Commons.CustomTextField
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.GeneralButtonStyle
import com.spotmap.spotmapandroid.Commons.TitleText

@Composable
fun AddLocationView(modifier: Modifier,
                    currentIndex: Int,
                    numberOfPage: Int,
                    nextButtonTapped: ()->Unit,
                    previousButtonTapped: ()->Unit) {

    TitleText("Choose accurate location (2/3)")
    BasicSpacer()
    CustomTextField(placeholder = "Name", onTextDidChange = {})
    BasicSpacer()
    GeneralButton("Next", onClick = { nextButtonTapped() })
    BasicSpacer()
    GeneralButton("Previous", onClick = { previousButtonTapped() }, style = GeneralButtonStyle.SECONDARY)
    BasicSpacer()
    CustomPageIndicator(currentIndex = remember { mutableStateOf(currentIndex) },
        indexCount = numberOfPage)
}