package com.spotmap.spotmapandroid.Screens.AddSpot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotmap.spotmapandroid.Commons.BasicSpacer
import com.spotmap.spotmapandroid.Commons.CustomTextField
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.SegmentedButton
import com.spotmap.spotmapandroid.R


@Composable
fun AddSpotScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.background(color = colorResource(id= R.color.BackgroundColor))) {

        Column(verticalArrangement = Arrangement.Top, modifier = modifier.padding(16.dp).background(color = colorResource(id = R.color.SecondaryColor), shape = RoundedCornerShape(16.dp)).padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 32.dp)) {
            AddGeneralInformationView(modifier)
        }
        Spacer(modifier = modifier.weight(1f))
    }
}
@Composable
fun AddGeneralInformationView(modifier: Modifier) {
    TitleText("Add general informations (1/3)")
    BasicSpacer()
    CustomTextField(placeholder = "Name", onTextDidChange = {})
    BasicSpacer()
    CustomTextField(placeholder = "Description", onTextDidChange = {})
    BasicSpacer()
    SegmentedButton(modifier = modifier.fillMaxWidth(), itemsTitles = listOf("STREET", "PARK"))
    BasicSpacer()
    GeneralButton("Next", onClick = {})

}

@Composable
fun TitleText(text: String) {
    Text(text,
        color = colorResource(id = R.color.LightColor),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp)
}

@Preview(showBackground = false)
@Composable
fun previewSpotScren() {
    AddSpotScreen(modifier = Modifier)
}