package com.spotmap.spotmapandroid.Commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.R

@Composable
fun CheckboxWithTitle(title:String, isChecked: MutableState<Boolean>) {

    Row(modifier = Modifier.height(40.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(id = R.color.PrimaryColor),
                uncheckedColor = colorResource(id = R.color.LightColor),
                checkmarkColor = colorResource(id = R.color.LightColor)
            ),
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it }
        )
        Text(
            text = title,
            color = colorResource(id = R.color.LightColor)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewCheckBox() {
    var isChecked = remember { mutableStateOf(true) }
    CheckboxWithTitle(title = "Need to pay to access", isChecked = isChecked )
}