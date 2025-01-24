package com.spotmap.spotmapandroid.Screens.AddSpot.Views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.CheckboxWithTitle
import com.spotmap.spotmapandroid.Commons.CustomPageIndicator
import com.spotmap.spotmapandroid.Commons.CustomTextField
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.LargeCustomTextField
import com.spotmap.spotmapandroid.Commons.SegmentedButton
import com.spotmap.spotmapandroid.Commons.TitleText

@Composable
fun AddGeneralInformationView(modifier: Modifier,
                              nameText: MutableState<String>,
                              descriptionText: MutableState<String>,
                              currentIndex: Int,
                              numberOfPage: Int,
                              nextButtonTapped: ()->Unit) {

    var isPayableSelected = remember { mutableStateOf(false) }
    var isDIYSelected = remember { mutableStateOf(false) }
    var isWaterSelected = remember { mutableStateOf(false) }


    var buttonIsEnable = if(nameText.value.isEmpty()) { false } else { true }

    TitleText("Add general informations (1/3)")
    BasicSpacer()
    CustomTextField(
        placeholder = "Name",
        onTextDidChange = {},
        textState = nameText)
    BasicSpacer()
    LargeCustomTextField(
        placeholder = "Description",
        onTextDidChange = {},
        textState = descriptionText)
    BasicSpacer()
    SegmentedButton(
        modifier = modifier.fillMaxWidth(),
        itemsTitles = listOf("STREET", "PARK"))
    BasicSpacer()
    CheckboxWithTitle(
        title = "Need to pay access",
        isChecked = isPayableSelected)
    CheckboxWithTitle(
        title = "Is D.I.Y",
        isChecked = isDIYSelected)
    CheckboxWithTitle(
        title = "Has access to water",
        isChecked = isWaterSelected)
    BasicSpacer()
    GeneralButton(
        "Next",
        onClick = { nextButtonTapped() },
        isEnable = buttonIsEnable)
    BasicSpacer()
    CustomPageIndicator(
        currentIndex = remember { mutableStateOf(currentIndex) },
        indexCount = numberOfPage)
}
