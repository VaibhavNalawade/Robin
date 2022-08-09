package com.vaibhav.robin.presentation.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.entities.remote.Comment
import com.vaibhav.robin.presentation.theme.Gradients
import com.vaibhav.robin.presentation.theme.RobinTheme

@Composable
fun ReviewCard(widthDp: Int, comment: Comment, heightDp: Float) {
   Surface(
        Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Gradients.getGradient()),
        tonalElevation = 10.dp, color = Color.Transparent,
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .width(widthDp.times(0.80f).dp)
                .height(heightDp.dp)
        ) {
            RatingWithDate(comment.date)
            ReviewContent(comment.headline, comment.content)
            UserTag(comment.firstName, comment.lastName, comment.likes, comment.profileImg)

        }
    }
}

@Composable
private fun UserTag(firstName: String, lastName: String, likes: Int, profileImg: String) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RobinAsyncImage(
            model = profileImg,
            contentDescription = "",
            modifier = Modifier
                .border(
                    shape = CircleShape,
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.tertiary
                )
                .size(42.dp)
                .clip(
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = firstName)
            Text(text = lastName, style = MaterialTheme.typography.bodySmall)
        }
        Divider(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight()
                .padding(vertical = 8.dp),
            color = Color.Red,
        )
        var checked by remember { mutableStateOf(false) }
        IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
            //Icon(imageVector = Icons.Filled.Favorite, contentDescription ="" )
            val tint by animateColorAsState(if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Localized description",
                tint = tint
            )
        }
        Text(text = "${getK(likes)} helpful", style = MaterialTheme.typography.labelSmall)

    }
}

fun getK(likes: Int) = when (likes) {
    in 1_000..999_999 -> "${likes.div(1000)}K"
    in 1_000_000..999_999_999 -> "${likes.div(1000000)}M"
    in 1_000_000_000..Int.MAX_VALUE -> "${likes.div(1000000000)}B"
    else -> "$likes"
}

@Composable
private fun ReviewContent(headline: String, content: String) {
    Text(
        text = headline,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge,
        maxLines = 2,
        modifier = Modifier.padding(vertical = 4.dp)
    )
    Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 5,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
private fun RatingWithDate(date: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.Star, contentDescription = "")
        Icon(imageVector = Icons.Default.Star, contentDescription = "")
        Icon(imageVector = Icons.Default.Star, contentDescription = "")
        Icon(imageVector = Icons.Default.Star, contentDescription = "")
        Icon(imageVector = Icons.Default.Star, contentDescription = "")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = date, style = MaterialTheme.typography.labelSmall)
    }
}

@Preview
@Composable
private fun Preview() {
    RobinTheme {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            ) {
                // ReviewCard(320f, heightDp =250f, comment = MockProvider().commentMock[2])
            }
        }
}