package com.spotmap.spotmapandroid.Screens.SpotDetails.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.Class.Publication
import com.spotmap.spotmapandroid.Class.SkaterLight
import com.spotmap.spotmapandroid.Class.SpotLight
import com.spotmap.spotmapandroid.Commons.NormalText
import com.spotmap.spotmapandroid.Commons.SmallNormalText
import com.spotmap.spotmapandroid.Commons.TitleButton
import com.spotmap.spotmapandroid.Commons.UserImageView
import com.spotmap.spotmapandroid.R

@Composable
fun PublicationView(publication: Publication, nameClick: () -> Unit, modifier: Modifier) {
    Column(modifier = modifier.padding(top = 8.dp, bottom = 8.dp)) {
        Row(verticalAlignment = Alignment.Top) {
            UserImageView(
                modifier = Modifier,
                url = publication.creator.photoUrl ?: "",
                height = 55.dp,
                onClick = {  })
            Spacer(Modifier.width(8.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TitleButton(
                            title = publication.creator.userName,
                            onClick = nameClick)
                        Spacer(Modifier.width(4.dp))
                        SmallNormalText(
                            publication.creationDate.timeSinceDate(),
                            color = colorResource(id = R.color.LightDarker1Color))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(20.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more),
                            contentDescription = null,
                            tint = colorResource(id = R.color.LightDarker1Color),
                        )
                    }

                }
                SmallNormalText(publication.spot.name)
            }
        }
        publication.description?.let {
            Spacer(Modifier.height(8.dp))
            NormalText(it,
                modifier = Modifier,
                color = colorResource(id= R.color.LightColor))
        }
    }
}

@Preview
@Composable
fun previewPublicationView() {

    val creator = SkaterLight(id="", userName = "Maxime", photoUrl = null)
    val spot = SpotLight(id = "", name = "Mon spot")
    val publication = Publication(id = "", description = "ma description", videoUrl = "", creator = creator, spot = spot)
    PublicationView(publication = publication, nameClick = {}, modifier = Modifier)
}