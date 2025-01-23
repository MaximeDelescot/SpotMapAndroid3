package com.spotmap.spotmapandroid.Screens.Account.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.R

@Composable
fun goToAccountView(modifier: Modifier,
                    title: String,
                    description: String,
                    goToButtonTapped: () -> Unit) {
    Row(modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Text(description,
            color = colorResource(id = R.color.LightDarker2Color))
        TextButton(onClick = { goToButtonTapped() },
            modifier = Modifier.padding(0.dp),
            contentPadding = PaddingValues(0.dp)) {
            Text(title,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.PrimaryColor))
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewAccount() {
    goToAccountView(Modifier,"Sign up","Already have an account", goToButtonTapped = {},)
}