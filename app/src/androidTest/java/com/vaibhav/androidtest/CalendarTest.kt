package com.vaibhav.androidtest

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.vaibhav.robin.presentation.common.DatePicker
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class CalendarTest {
    /* TODO - able to select specific year and month @Calendar.kt*/

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calendarSelection() {
        composeTestRule.setContent {
            var showPicker by remember { mutableStateOf(value = false) }
            var textDate: String? by remember { mutableStateOf(null) }

            Column() {
                if (showPicker)
                    DatePicker(
                        onDateSelected = { selectedDate ->
                            val formatDate = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                            textDate = formatDate.toString()
                        },
                        onDismissRequest = {
                            showPicker = false
                        }
                    )

                Button(onClick = { showPicker = !showPicker }) {
                    Text(text = "ShowDatePicker")
                }

                textDate?.let { Text(text = it) }
            }
        }
        composeTestRule.onNodeWithText("ShowDatePicker").assertExists().performClick()
        composeTestRule.onNodeWithText("23").performClick()
        composeTestRule.onNodeWithText("Ok").performClick()

    }
}