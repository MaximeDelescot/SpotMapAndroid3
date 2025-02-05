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
                              typeItems: List<String>,
                              typeIndex: MutableState<Int>,
                              needToPayIsSelected: MutableState<Boolean>,
                              shelteredFromRainIsSelected: MutableState<Boolean>,
                              hasFixedHoursIsSelected: MutableState<Boolean>,
                              hasLightingIsSelected: MutableState<Boolean>,
                              nextButtonTapped: ()->Unit) {

    var buttonIsEnable = if(nameText.value.isEmpty()) { false } else { true }

    TitleText(text = "Add general informations (1/3)")
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
        indexSelected = typeIndex,
        modifier = modifier.fillMaxWidth(),
        itemsTitles = typeItems)
    BasicSpacer()
    CheckboxWithTitle(
        title = "Need to pay access",
        isChecked = needToPayIsSelected)
    CheckboxWithTitle(
        title = "Sheltered from the rain",
        isChecked = shelteredFromRainIsSelected)
    CheckboxWithTitle(
        title = "Has fixed opening hours",
        isChecked = hasFixedHoursIsSelected)
    CheckboxWithTitle(
        title ="Has lighting",
        isChecked = hasLightingIsSelected)
    BasicSpacer()
    GeneralButton(
        modifier.fillMaxWidth(),
        "Next",
        onClick = { nextButtonTapped() },
        isEnable = buttonIsEnable)
    BasicSpacer()
    CustomPageIndicator(
        currentIndex = remember { mutableStateOf(currentIndex) },
        indexCount = numberOfPage)
}
