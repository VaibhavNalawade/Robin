/*
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.vaibhav.robin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.R
import com.vaibhav.robin.data.unsplash.model.Results
import com.vaibhav.robin.presentation.theme.Values.Dimens
import com.vaibhav.robin.presentation.theme.Values.Dimens.brandingImageSize

@Composable
fun CustomListHorizontal(text: String = "Title", listResults: List<Results>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
            .padding(vertical = Dimens.gird_one, horizontal = Dimens.gird_two),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = typography.headlineLarge.copy(color = colorScheme.onSurface),
        )
        TextButton(modifier = Modifier, onClick = { */
/* Do something! *//*
 }) { Text("SEE ALL") }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorScheme.surface),

        contentPadding = PaddingValues(vertical = Dimens.gird_half, horizontal = Dimens.gird_two),

        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (listResults.isEmpty()) {
            item { HorizontalGrid(result = Results(), result1 = Results(), true) }
            item { HorizontalGrid(result = Results(), result1 = Results(), true) }
        }

        items(listResults.size) { index ->

            if (index % 2 == 0)
                HorizontalGrid(
                    result = listResults[index], //Todo Not a Good Solution Find alternative
                    result1 = listResults[index + 1]
                )
        }
    }
}

@Composable
fun HorizontalGrid(result: Results, result1: Results, placeHolder: Boolean = false) {
    Column {
        ItemCard(unsplashGet = result, placeHolder)
        Spacer(modifier = Modifier.height(Dimens.gird_two))
        Row {
            Spacer(modifier = Modifier.width(Dimens.gird_four))
            ItemCard(unsplashGet = result1, placeHolder)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(unsplashGet: Results, placeHolder: Boolean = false) {
    OutlinedCard(
        modifier = Modifier
            .size(220.dp),  //Todo Not gone work on other Screen Sizes ,
        shape = shapes.medium,
    ) {

        Column {

            Box(
                modifier = Modifier
                    .fillMaxHeight(.75f)
                    .placeholder(placeHolder),
                contentAlignment = Alignment.BottomCenter
            ) {

                RobinAsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = brandingImageSize.div(2)),
                    contentScale = ContentScale.FillWidth,
                    model = unsplashGet.urls?.small,
                    contentDescription = stringResource(id = R.string.product_image)
                )

                CircularImage(
                    modifier = Modifier.size(brandingImageSize),
                    Image = unsplashGet.user?.profileImage?.small?:"null",
                    contentDescrption =  stringResource(id = R.string.branding_Image)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.gird_half))

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_one),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .placeholder(placeHolder),
                    text = unsplashGet.user?.name ?: "PlaceHolder",
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    style = typography.titleMedium
                )

                Spacer(modifier = Modifier.height(Dimens.gird_quarter))

                Text(
                    modifier = Modifier
                        .placeholder(placeHolder),
                    text = "$123",
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    style = typography.bodyMedium.copy(
                        colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(Dimens.gird_quarter))
            }
        }
    }
}


*/
