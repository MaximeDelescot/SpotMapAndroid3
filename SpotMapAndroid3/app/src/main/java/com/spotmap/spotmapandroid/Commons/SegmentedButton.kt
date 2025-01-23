package com.spotmap.spotmapandroid.Commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.R


@Composable
fun SegmentedButton(modifier: Modifier = Modifier,
                    itemsTitles: List<String>,
                    indexSelected: MutableState<Int> = remember { mutableStateOf(0) },
) {

    Row(modifier.fillMaxWidth()
        .height(40.dp)
        .clip(RoundedCornerShape(
            topStart = 12.dp,
            bottomEnd = 12.dp,
            topEnd = 12.dp,
            bottomStart = 12.dp))
        .background(color = colorResource(id= R.color.BackgroundColor)),
        verticalAlignment = Alignment.CenterVertically) {

        itemsTitles.forEachIndexed{ index, item ->
            SegmentedItem(
                modifier.weight(1f),
                title= item,
                index = index,
                indexSelected = indexSelected.value,
                numberOfItem = itemsTitles.count(),
                onTap = { yo ->
                    indexSelected.value = yo
                })
        }
    }
}

@Composable
fun SegmentedItem(modifier: Modifier, title: String, index: Int, indexSelected: Int, numberOfItem: Int, onTap: (Int) -> Unit) {

    val isSelected = index == indexSelected
    val roundedShape = if (index == 0) {
        RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
    } else if (index == numberOfItem - 1){
        RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
    } else {
        RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp)
    }

    val modif = if (isSelected) { modifier.padding(1.dp).clip(roundedShape).background(color = colorResource(id=R.color.SecondaryColor)).fillMaxSize() } else { modifier }
    val textColor = if (isSelected) { colorResource(id= R.color.LightColor) } else { colorResource(id= R.color.LightDarker2Color) }
    TextButton(
        onClick = { onTap(index) },
        modifier = modif)
    { Text(
        title,
        color = textColor)
    }
}


@Preview
@Composable
fun previewSegment() {
    val items = listOf("PARK", "STREET")
    SegmentedButton(itemsTitles = items)
}