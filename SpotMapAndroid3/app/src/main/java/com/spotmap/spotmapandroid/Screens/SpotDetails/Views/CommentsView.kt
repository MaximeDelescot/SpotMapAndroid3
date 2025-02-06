package com.spotmap.spotmapandroid.Screens.SpotDetails.Views

import android.text.format.DateUtils
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spotmap.spotmapandroid.Class.Comment
import com.spotmap.spotmapandroid.Commons.CustomTextField
import com.spotmap.spotmapandroid.Commons.GeneralButton
import com.spotmap.spotmapandroid.Commons.NormalButton
import com.spotmap.spotmapandroid.Commons.NormalText
import com.spotmap.spotmapandroid.Commons.SmallNormalText
import com.spotmap.spotmapandroid.Commons.TitleButton
import com.spotmap.spotmapandroid.Commons.Utils.convertToFastestUrl
import com.spotmap.spotmapandroid.Screens.UserDetails.Views.UserImageView
import com.spotmap.spotmapandroid.R
import com.spotmap.spotmapandroid.Screens.SpotDetails.SpotDetailsScreenViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.Date

@Composable
fun CommentsView(comments: List<Comment>, commentsCount: Int, viewModel: SpotDetailsScreenViewModel) {

    val commentTextState = remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextField(
                placeholder = "Add a comment",
                backgroundColorId = R.color.SecondaryColor,
                textState = commentTextState,
                onTextDidChange = {},
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { viewModel.sendComment(commentTextState.value) }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_reply),
                    contentDescription = "Add comment",
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.LightColor))
                )
            }
        }
        if (comments.size == 0) {
            Spacer(Modifier.height(16.dp))
            SmallNormalText(
                "No comment yet",
                modifier= Modifier,
                color = colorResource(id=R.color.LightDarker1Color))
        } else {
            for (comment in comments) {
                Spacer(Modifier.height(16.dp))
                CommentView(comment = comment, modifier = Modifier.fillMaxWidth())
            }

            if (commentsCount > 2) {
                val countPlus = commentsCount - 2
                NormalButton(
                    colorId = R.color.LightDarker1Color,
                    title = "See more (+$countPlus)",
                    onClick = {})
            }
        }
    }
}

@Composable
fun CommentView(comment: Comment, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.Top, modifier = modifier) {
        UserImageView(
            modifier = Modifier,
            url = comment.creator.photoUrl?.convertToFastestUrl(),
            height = 55.dp,
            onClick = {  })
        Spacer(Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TitleButton(
                    title = comment.creator.userName,
                    onClick = {})
                Spacer(Modifier.width(4.dp))
                SmallNormalText(
                    comment.creationDate.timeSinceDate(),
                    color = colorResource(id = R.color.LightDarker1Color))
            }
            NormalText(comment.content)
        }
    }
}

fun Date.timeSinceDate(): String {
    return DateUtils.getRelativeTimeSpanString(
        this.time,
        System.currentTimeMillis(),
        DateUtils.MINUTE_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE // Utilise des abréviations si possible
    ).toString()
}


