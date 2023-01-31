package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.android.style.LineHeightSpan
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import java.util.Locale

@Composable
fun ProfileInitial(
    modifier: Modifier = Modifier,
    profileName: String
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .padding(Dimens.gird_quarter)
            .clip(CircleShape)
            .background(colorScheme.tertiaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = profileName
                .split(' ')
                .map { it.first().toString() }
                .reduce { acc, s ->
                    (acc + s).uppercase(Locale.getDefault())
                },
            style = typography.titleSmall,
            textAlign = TextAlign.Center,
            color = colorScheme.onTertiaryContainer
        )
    }
}

@Preview
@Composable
fun ProfileInitialPreview() {
    RobinAppPreview {
        ProfileInitial(profileName = "John Muir")
    }
}