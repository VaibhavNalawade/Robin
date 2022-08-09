package com.vaibhav.robin.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.entities.ui.common.DropdownOption
import com.vaibhav.robin.entities.ui.state.DropdownState
import com.vaibhav.robin.presentation.theme.Values.Dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(
    dropdownOptionList: List<DropdownOption>,
    state: MutableState<DropdownState>
) {

    var expanded by remember { mutableStateOf(false) }
    var index by remember { mutableStateOf(state.value.selectedIndex) }

    Column(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = if (!state.value.error) colorScheme.outline else colorScheme.error
                    ),
                    shape = shapes.extraSmall
                ),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(Dimens.gird_two))
                dropdownOptionList[index].leadingIcon?.let { vector ->
                    Icon(
                        imageVector = vector,
                        contentDescription = dropdownOptionList[index].contentDescription
                    )
                }
                Spacer(
                    modifier = Modifier.width(Dimens.gird_two)
                )
                Text(
                    text = dropdownOptionList[index].options,
                    style = typography.bodyLarge
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(

                        modifier = Modifier.clearAndSetSemantics { },
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Trailing icon for exposed dropdown menu",
                            tint = colorScheme.onSurface,
                            modifier = Modifier.rotate(
                                if (expanded) 180f
                                else 360f
                            )
                        )
                    }
                }
            }
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
    RobinAppPreviewScaffold {
        Box(
            modifier = Modifier.padding(
                horizontal = Dimens.gird_two,
                vertical = Dimens.gird_one
            ), contentAlignment = Alignment.Center
        ) {
            ExposedDropdownMenuBox(
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
    RobinAppPreviewScaffold {
        Box(
            modifier = Modifier.padding(
                horizontal = Dimens.gird_two,
                vertical = Dimens.gird_one
            ), contentAlignment = Alignment.Center
        ) {
            ExposedDropdownMenuBox(
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