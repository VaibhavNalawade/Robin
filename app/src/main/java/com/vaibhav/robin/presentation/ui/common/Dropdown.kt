package com.vaibhav.robin.presentation.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.models.common.DropdownOption
import com.vaibhav.robin.presentation.models.state.DropdownState
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobinDropdownMenuBox(
    dropdownOptionList: List<DropdownOption>,
    state: MutableState<DropdownState>,
    modifier:Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }
    var index by remember { mutableStateOf(state.value.selectedIndex) }

    Column(modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier.menuAnchor(),
                value = dropdownOptionList[index].options,
                onValueChange = {  },
                label = { Text("Label") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dropdownOptionList.forEachIndexed { selectedIndex, selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Row {
                                selectionOption.leadingIcon?.let { vector ->
                                    SpacerHorizontalTwo()
                                    Icon(
                                        imageVector = vector,
                                        contentDescription = selectionOption.contentDescription
                                    )
                                }
                                Text(
                                    text = selectionOption.options,
                                    style = typography.bodyLarge
                                )
                            }
                        }, onClick = {
                            state.value = state.value.copy(
                                text = selectionOption.options,
                                selectedIndex = selectedIndex
                            )
                            index = selectedIndex
                            expanded = false
                        })
                }
            }
        }
        state.value.errorMessage?.let { stringResource ->
            Text(
                modifier = Modifier.testTag("error"),
                text = stringResource.asString(),
                style = typography.bodySmall.copy(colorScheme.error)
            )
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DropdownPreviewLight() {
    RobinAppPreview {
        Box(
            modifier = Modifier.padding(
                horizontal = Dimens.gird_two,
                vertical = Dimens.gird_one
            ), contentAlignment = Alignment.Center
        ) {
            RobinDropdownMenuBox(
                listOf(DropdownOption(options = "op")),
                remember {
                    mutableStateOf(DropdownState())
                }
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DropdownPreviewDark() {
    RobinAppPreview {
        Box(
            modifier = Modifier.padding(
                horizontal = Dimens.gird_two,
                vertical = Dimens.gird_one
            ), contentAlignment = Alignment.Center
        ) {
            RobinDropdownMenuBox(
                listOf(
                    DropdownOption(
                        options = "op",
                    )
                ),
                remember {
                    mutableStateOf(DropdownState())
                }
            )
        }
    }
}