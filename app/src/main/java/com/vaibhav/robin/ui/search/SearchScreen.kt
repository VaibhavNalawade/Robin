@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.vaibhav.robin.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.vaibhav.robin.ui.common.GridVertical
import com.vaibhav.robin.ui.common.RobinBar
import com.vaibhav.robin.ui.theme.RobinTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(navController: NavHostController) {

    val viewModel: SearchViewModel = viewModel()
    val topAppBarScrollState = rememberTopAppBarState()
    val textState by viewModel.searchQuery.collectAsState()
    var navArgumentSearched by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    val argument = navController.currentBackStackEntry?.arguments?.getString("query") ?: ""
    if (argument.isNotBlank() && argument != "null") {
        viewModel.updateQuery(argument)
        viewModel.search()
        focusManager.clearFocus()
        navArgumentSearched = true
    }

    val focusRequester = remember { FocusRequester() }
    if (!navArgumentSearched)
        LaunchedEffect(true) {
            focusRequester.requestFocus()
        }
    val textFiledStyle = RobinTypography.labelLarge.copy(
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )

    RobinBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
            BasicTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = textState,
                onValueChange = { viewModel.updateQuery(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.search()
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true,
                textStyle = textFiledStyle,
                decorationBox = { innerTextField ->
                    Surface(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Box(
                            Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (textState.isEmpty()) {
                                Text(
                                    "Search in Robin Store",
                                    style = textFiledStyle
                                )
                            }
                            innerTextField()
                        }
                    }
                })
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                viewModel.search()
                focusManager.clearFocus()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarScrollState)
    ) {
        Column {
            GridVertical(
                resultsLazyPagingItems = viewModel.searchedImages.collectAsLazyPagingItems()
            )
        }

    }
}