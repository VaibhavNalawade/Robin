package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold

@Composable
fun Star(modifier:Modifier=Modifier,onClick: (Int) -> Unit, stars: Int) {
    Row(modifier = modifier) {
        for (i in 0..4 step 1) {
            IconButton(modifier = Modifier.size(64.dp), onClick = { onClick(1 + i) }, content = {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = if (stars > i) painterResource(id = R.drawable.star_rate_fill1_wght200_grad200_opsz48)
                    else painterResource(id = R.drawable.star_rate_fill0_wght200_grad200_opsz48),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            })
        }
    }
}

@Preview
@Composable
private fun StarPreview() {
    RobinAppPreviewScaffold {
    Star(onClick = {}, stars =5 )
    }
}