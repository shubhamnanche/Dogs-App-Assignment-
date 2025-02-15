package com.svg.dogsapp.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.svg.dogsapp.R
import com.svg.dogsapp.domain.model.DogImageModel
import com.svg.dogsapp.ui.theme.ButtonColor
import com.svg.dogsapp.ui.theme.ButtonColorDark

@Composable
fun RecentlyGeneratedDogsScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: DogImageViewModel
) {
    val TAG = "RecentDogsScreen"
    val result = viewModel.recentlyGeneratedDogs.value

    LaunchedEffect(Unit) {
        viewModel.getRecentlyGeneratedDogs()
        Log.d(TAG, "RecentDogsScreen: size > ${result.data?.size}")
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (result.isLoading) {
            CircularProgressIndicator()
        }
        if (result.error.isNotBlank()) {
            SelectionContainer {
                Text(text = result.error)
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.8f)
        ) {
            if (result.data?.isEmpty() == true) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.no_recently_generated_dogs))
                    }
                }
            } else {
                result.data?.let { dogs ->
                    items(dogs.size) {
                        DogImage(
                            dogImageModel = dogs[it],
                            viewModel = viewModel
                        )
                    }
                }

            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.clearRecentlyGeneratedDogs()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.ButtonColor,
                contentColor = Color.White
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color.ButtonColorDark
            )
        ) {
            Text(text = stringResource(id = R.string.clear_dogs))
        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}

@Composable
fun DogImage(dogImageModel: DogImageModel, viewModel: DogImageViewModel) {

    LaunchedEffect(dogImageModel.url) {
        viewModel.loadImageBitmap(dogImageModel)
    }
    val bitmapState = remember(dogImageModel.url) {
        derivedStateOf { viewModel.getBitmapFromCache(dogImageModel.url) }
    }

    val bitmap = bitmapState.value

    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Dog Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .requiredWidth(200.dp)
                .aspectRatio(1f)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(28.dp),
                    clip = false
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable(onClick = {})
                .background(MaterialTheme.colorScheme.surface)

        )
    } else {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .requiredWidth(200.dp)
                .aspectRatio(1f)
        ) {
            CircularProgressIndicator()
        }
    }

}