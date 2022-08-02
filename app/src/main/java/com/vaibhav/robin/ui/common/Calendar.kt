package com.vaibhav.robin.ui.common

import android.text.format.DateFormat
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.android.material.color.DynamicColors
import com.vaibhav.robin.R
import java.util.Calendar
import java.util.Date
/*
* TODO - able to select specific year and month*/
@Composable
fun DatePicker(
    maxDate: Long? = null,
    minDate: Long? = null,
    onDateSelected: (Date) -> Unit,
    onDismissRequest: () -> Unit
) {
    val selDate = remember { mutableStateOf(Calendar.getInstance().time) }

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = colorScheme.surface,
                    shape = shapes.medium
                )
        ) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = 72.dp)
                    .fillMaxWidth()
                    .background(
                        color = colorScheme.primary,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.select_date),
                    style = typography.labelMedium,
                    color = colorScheme.onPrimary
                )

                SpacerVerticalThree()

                Text(
                    text = DateFormat.format("MMM d, yyyy", selDate.value).toString(),
                    style = typography.titleMedium,
                    color = colorScheme.onPrimary
                )

                SpacerVerticalTwo()
            }

            CustomCalendarView(
                minDate,
                maxDate,
                onDateSelected = {
                    selDate.value = it
                }
            )

            SpacerVerticalOne()

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.textButtonColors(),
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                    )
                }

                Button(
                    onClick = {
                        val newDate = selDate.value
                        onDateSelected(
                            Date(
                                maxOf(
                                    minOf(maxDate ?: Long.MAX_VALUE, newDate.time),
                                    minDate ?: Long.MIN_VALUE
                                )
                            )
                        )
                        onDismissRequest()
                    },
                    colors = ButtonDefaults.textButtonColors(),
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                    )
                }

            }
        }
    }
}

/**
 * Used at [DatePicker] to create the calendar picker.
 * @author Arnau Mora, Joao Gavazzi
 * @param minDate The minimum date allowed to be picked.
 * @param maxDate The maximum date allowed to be picked.
 * @param onDateSelected Will get called when a date is selected.
 */
@Composable
private fun CustomCalendarView(
    minDate: Long? = null,
    maxDate: Long? = null,
    onDateSelected: (Date) -> Unit
) {
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            val dynamicContext = DynamicColors.wrapContextIfAvailable(context)
            CalendarView(ContextThemeWrapper(dynamicContext, R.style.CalenderViewCustom))
        },
        update = { view ->
            if (minDate != null)
                view.minDate = minDate
            if (maxDate != null)
                view.maxDate = maxDate

            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                onDateSelected(
                    Calendar
                        .getInstance()
                        .apply {
                            set(year, month, dayOfMonth)
                        }
                        .time
                )
            }
        }
    )
}