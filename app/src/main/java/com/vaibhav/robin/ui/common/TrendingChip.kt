package com.vaibhav.robin.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.vaibhav.robin.RobinAppPreviewScaffold
import com.vaibhav.robin.RobinDestinations
import com.vaibhav.robin.entities.remote.TrendingChipData
import com.vaibhav.robin.ui.RobinVectorLoader
import com.vaibhav.robin.ui.theme.Values.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingChipRow(trendingChipState: TrendingChipState,navController: NavController) {
    when (trendingChipState) {
        is TrendingChipState.Loading -> {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.gird_one),
                contentPadding = PaddingValues(
                    horizontal = Dimens.gird_two,
                    vertical = Dimens.gird_half
                )
            ) {
                items(10) {
                    ChipDummy()
                }
            }
        }
        is TrendingChipState.Complete -> {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.gird_one),
                contentPadding = PaddingValues(
                    horizontal = Dimens.gird_two,
                )
            ) {
                items(items = trendingChipState.chipData, itemContent = { item ->
                    SuggestionChip(label = { Text(text = item.name) },
                        icon = {
                            item.imageVector?.let { imageVector ->
                                RobinVectorLoader.load(imageVector)?.let {
                                    Icon(
                                        modifier = Modifier.size(
                                            SuggestionChipDefaults.IconSize
                                        ), imageVector = it,
                                        contentDescription = item.description
                                    )
                                }
                            }
                        },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            iconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        onClick = {navController.navigate(RobinDestinations.searchQuery("${item.name}"))}
                    )
                })
            }
        }
        else -> {}
    }

}

sealed class TrendingChipState {
    object Loading : TrendingChipState()
    data class Complete(val chipData: List<TrendingChipData>) : TrendingChipState()
    data class Error(val exception: Exception) : TrendingChipState()
}


@Preview
@Composable
fun TrendingChipLoading() {
    RobinAppPreviewScaffold {
        TrendingChipRow(
            TrendingChipState.Loading, NavController(LocalContext.current)
        )
    }
}

@Preview
@Composable
fun TrendingChipComplete() {
    RobinAppPreviewScaffold {
        TrendingChipRow(
            TrendingChipState.Complete(
                listOf(
                    TrendingChipData("#Trending", "Restaurant", 0),
                    TrendingChipData("Restaurant", "Restaurant", 2),
                    TrendingChipData("Kitchen", "Restaurant", null),
                    TrendingChipData("Books", "Restaurant", 1)
                )
            ), NavController(LocalContext.current)
        )
    }
}

