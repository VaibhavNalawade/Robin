package com.vaibhav.robin.presentation.account

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Boy
import androidx.compose.material.icons.filled.Girl
import androidx.compose.material.icons.twotone.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.entities.ui.common.DropdownOption
import com.vaibhav.robin.navigation.RobinDestinations
import com.vaibhav.robin.presentation.common.*
import com.vaibhav.robin.presentation.theme.Values.Dimens
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateAndGenderSelect(navController: NavHostController, viewModel: DateAndGenderViewModel) {

    val date = viewModel.date
    val gender = viewModel.gender
    Column(
        modifier = Modifier
            .padding(horizontal = Dimens.gird_two)
    ) {

        SpacerVerticalFour()

        Text(
            text = stringResource(id = R.string.personal_details),
            style = typography.headlineMedium
        )

        SpacerVerticalTwo()

        Text(
            text = stringResource(id = R.string.signup_message),
            style = typography.bodyMedium
        )

        SpacerVerticalFour()

        var showPicker by remember { mutableStateOf(value = false) }

        if (showPicker)
            DatePicker(
                onDateSelected = { selectedDate ->
                    val formatDate = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                    date.value = date.value.copy(text = formatDate.format(selectedDate))
                },
                onDismissRequest = {
                    showPicker = false
                }
            )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (!viewModel.date.value.error) colorScheme.outline
                        else colorScheme.error
                    ),
                    shape = shapes.extraSmall,
                )
                .clickable(onClick = { showPicker = true })
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(Dimens.gird_two)
            ) {

                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.TwoTone.CalendarMonth,
                    contentDescription = stringResource(id = R.string.calender)
                )

                SpacerHorizontalTwo()

                Text(
                    text =
                    if (date.value.text.isBlank()) stringResource(id = R.string.select_a_date)
                    else date.value.text
                )
            }
        }
        viewModel.date.value.errorMessage?.let {
            Text(
                text = it.asString(),
                style = typography.bodySmall.copy(color = colorScheme.error)
            )
        }

        SpacerVerticalTwo()

        val array = stringArrayResource(id = R.array.gender_selection)

        val list = listOf(
            DropdownOption(array[0]),
            DropdownOption(array[1], Icons.Filled.Boy),
            DropdownOption(array[2], Icons.Filled.Girl)
        )

        ExposedDropdownMenuBox(
            dropdownOptionList = list,
            state = gender
        )

        gender.value.errorMessage?.let {
            Text(
                text = it.asString(),
                style = typography.bodySmall.copy(color = colorScheme.error)
            )
        }

        SpacerVerticalFour()

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                viewModel.predateDateGender(array) {
                    navController.navigate(RobinDestinations.ADDRESS_AND_PHONE)
                }
            },
            content = {
                Text(text = "Next")
            }
        )
    }
}

@Preview
@Composable
fun DateAndGenderSelectLightPreview() {
    RobinAppPreviewScaffold {
        DateAndGenderSelect(
            NavHostController(LocalContext.current),
            viewModel = viewModel()
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DateAndGenderSelectDarkPreview() {
    RobinAppPreviewScaffold {
        DateAndGenderSelect(
            NavHostController(LocalContext.current),
            viewModel = viewModel()

        )
    }
}