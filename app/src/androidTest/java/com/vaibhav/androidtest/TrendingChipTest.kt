package com.vaibhav.androidtest

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import com.vaibhav.robin.RobinAppPreviewScaffold
import com.vaibhav.robin.entities.remote.TrendingChipData
import com.vaibhav.robin.ui.common.TrendingChipRow
import com.vaibhav.robin.ui.common.TrendingChipState
import org.junit.Rule
import org.junit.Test

class TrendingChipTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingStateTest() {
        composeTestRule.setContent {
            RobinAppPreviewScaffold {
                val arg = remember {
                    mutableStateOf<TrendingChipState>(TrendingChipState.Loading)
                }
                TrendingChipRow(arg.value, NavController(LocalContext.current))
            }
        }
        composeTestRule.onRoot().onChild().onChild().onChildren().onLast().performScrollTo()
        composeTestRule.onRoot().printToLog("Log")
    }

    @Test
    fun loadingCompleteState() {
        composeTestRule.setContent {
            RobinAppPreviewScaffold {
                val arg = TrendingChipState.Complete(
                    listOf(
                        TrendingChipData("#Trending", "Restaurant", 0),
                        TrendingChipData("Restaurant", "Restaurant", 2),
                        TrendingChipData("Kitchen", "Restaurant", null),
                        TrendingChipData("Books", "Restaurant", 1),
                        TrendingChipData("Boos", "Restaurant", 1)
                    )
                )
                TrendingChipRow(arg,NavController(LocalContext.current))
            }
        }
        composeTestRule.onNodeWithText("#Trending").assertContentDescriptionEquals("Restaurant")
            .performScrollTo()
        composeTestRule.onNodeWithText("Restaurant").assertContentDescriptionEquals("Restaurant")
            .performScrollTo()
        composeTestRule.onNodeWithText("Books").assertContentDescriptionEquals("Restaurant")
            .performScrollTo()
        composeTestRule.onNodeWithText("Boos").assertContentDescriptionEquals("Restaurant")
            .performScrollTo().performClick()

    }
}