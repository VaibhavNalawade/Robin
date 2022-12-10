package com.vaibhav.androidtest

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Factory
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.models.common.DropdownOption
import com.vaibhav.robin.presentation.models.state.DropdownState
import com.vaibhav.robin.presentation.UiText
import com.vaibhav.robin.presentation.ui.common.RobinDropdownMenuBox
import org.junit.Rule
import org.junit.Test

class DropDownMenuTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val options = listOf(
        DropdownOption(options = "Select", leadingIcon = Icons.Filled.Factory),
        DropdownOption(options = "A", leadingIcon = Icons.Filled.Factory),
        DropdownOption(options = "B", leadingIcon = Icons.Filled.Factory),
        DropdownOption(options = "C", leadingIcon = Icons.Filled.Factory),
        DropdownOption(options = "D", leadingIcon = Icons.Filled.Factory),
        DropdownOption(options = "E", leadingIcon = Icons.Filled.Factory),
        DropdownOption(options = "F", leadingIcon = Icons.Filled.Factory),
        DropdownOption(options = "G", leadingIcon = Icons.Filled.Factory)

    )
    private val state: MutableState<DropdownState> = mutableStateOf(DropdownState())


    @Test
    fun dropDownMenuTestClickTest() {        composeTestRule.setContent {
            RobinAppPreview {
                RobinDropdownMenuBox(
                    state = state,
                    dropdownOptionList = options
                )
            }
        }
        composeTestRule.onNodeWithText("Select").assertExists().performClick()
        assert(state.value.selectedIndex == 0)
        composeTestRule.onNodeWithText("A").performScrollTo().assertExists()
            .performClick()
        composeTestRule.onNodeWithText("A").assertExists().performClick()
        assert(state.value.text == "A")
        assert(state.value.selectedIndex == 1)
        composeTestRule.onNodeWithText("B").performScrollTo().assertExists()
            .performClick()
        composeTestRule.onNodeWithText("B").assertExists().performClick()
        assert(state.value.text == "B")
        assert(state.value.selectedIndex == 2)
        composeTestRule.onNodeWithText("C").performScrollTo().assertExists()
            .performClick()
        composeTestRule.onNodeWithText("C").assertExists().performClick()
        assert(state.value.text == "C")
        assert(state.value.selectedIndex == 3)
        composeTestRule.onNodeWithText("D").performScrollTo().assertExists()
            .performClick()
        composeTestRule.onNodeWithText("D").assertExists().performClick()
        assert(state.value.text == "D")
        assert(state.value.selectedIndex == 4)
        composeTestRule.onNodeWithText("E").performScrollTo().assertExists()
            .performClick()
        composeTestRule.onNodeWithText("E").assertExists().performClick()
        assert(state.value.text == "E")
        assert(state.value.selectedIndex == 5)
        composeTestRule.onNodeWithText("F").performScrollTo().assertExists()
            .performClick()
        composeTestRule.onNodeWithText("F").assertExists().performClick()
        assert(state.value.text == "F")
        assert(state.value.selectedIndex == 6)
        composeTestRule.onNodeWithText("G").performScrollTo().assertExists()
            .performClick()
        composeTestRule.onNodeWithText("G").assertExists().performClick()
        assert(state.value.text == "G")
        assert(state.value.selectedIndex == 7)

    }
    @Test
    fun checkDisplayErrorTest(){
        composeTestRule.setContent {
            RobinAppPreview {
                state.value=state.value.copy(error=true, errorMessage = UiText.DynamicString("Error Occurred"))
                RobinDropdownMenuBox(
                    state = state,
                    dropdownOptionList = options
                )
            }
        }
        composeTestRule.onNodeWithTag("error").assertExists()
        assert(state.value.error)
        state.value=state.value.copy(error=false, errorMessage = null)
        composeTestRule.onNodeWithTag("error").assertDoesNotExist()
        composeTestRule.onNodeWithText("Error Occurred").assertDoesNotExist()
    }
}