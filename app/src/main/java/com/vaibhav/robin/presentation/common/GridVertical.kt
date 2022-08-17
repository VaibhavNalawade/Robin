package com.vaibhav.robin.presentation.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.vaibhav.robin.R
import com.vaibhav.robin.data.unsplash.model.Results
import com.vaibhav.robin.presentation.theme.Values

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridVertical(resultsLazyPagingItems: LazyPagingItems<Results>): Unit {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp), contentPadding = PaddingValues(top = 96.dp, bottom = Values.Dimens.gird_four),content = {
        items(resultsLazyPagingItems.itemCount) {
            resultsLazyPagingItems[it]?.let { it1 -> GridCellItem(it1) }
        }
    })
}

@Composable
fun GridCellItem(unsplashGet: Results): Unit {
    Surface(
        modifier = Modifier.padding(2.dp),
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            Modifier.clickable { },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier,
                contentAlignment = Alignment.BottomCenter
            ) {
                RobinAsyncImage(
                    modifier = Modifier
                        .size(220.dp)
                        .padding(bottom = 14.dp), contentScale = ContentScale.Crop,
                    model = unsplashGet.urls?.small,
                    contentDescription = ""
                )
                /*Image(
                    painter = painterResource(id = R.drawable.apple),
                    contentDescription = "",
                    modifier = Modifier.size(28.dp)
                )*/
            }
            unsplashGet.user?.name?.let {
                Text(
                    modifier = Modifier.padding(
                        vertical = 2.dp,
                        horizontal = 4.dp
                    ), text = it, style = MaterialTheme.typography.titleMedium
                )
            }
            Text(text = "$ 39", style = MaterialTheme.typography.bodySmall)

        }
    }
}
