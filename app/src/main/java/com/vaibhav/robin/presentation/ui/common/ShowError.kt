package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.ExceptionHandler
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.ui.theme.Values

@Composable
fun ShowError(
    exception: Exception,
    retry: () -> Unit
) {
    val errorhandler = remember {
        ExceptionHandler(exception)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.primary.copy(alpha = 0.05f)
    ) {
        Box {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        horizontal = Values.Dimens.gird_two,
                        vertical = Values.Dimens.gird_four
                    ),
                text = stringResource(id = R.string.app_name),
                style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(Values.Dimens.gird_four),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = colorScheme.errorContainer
                ) {
                    Icon(
                        modifier = Modifier
                            .size(128.dp)
                            .padding(Values.Dimens.gird_three),
                        imageVector = errorhandler.icon,
                        contentDescription = null,
                        tint = colorScheme.error
                    )
                }
                SpacerVerticalTwo()
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorhandler.title.asString(),
                    textAlign = TextAlign.Center,
                    style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
                )
                SpacerVerticalOne()
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorhandler.message.asString(),
                    textAlign = TextAlign.Center,
                    style = typography.bodyMedium.copy(colorScheme.onSurfaceVariant)
                )
                SpacerVerticalTwo()
                Button(onClick = {
                    retry()
                }) {
                    Text(text = stringResource(R.string.try_again))
                }
            }
        }
    }
}

@Preview
@Composable
fun ErrorPreview() {
    RobinAppPreviewScaffold {
        ShowError(
           Exception()
        ){}
    }
}