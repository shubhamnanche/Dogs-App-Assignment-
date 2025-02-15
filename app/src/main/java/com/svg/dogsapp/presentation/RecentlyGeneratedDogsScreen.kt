package com.svg.dogsapp.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.svg.dogsapp.R

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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
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
                    Text(text = stringResource(id = R.string.no_recently_generated_dogs))
                }
            } else {
                result.data?.let { dogs ->
                    items(dogs.size) {
                        viewModel.loadImageBitmap(dogs[it])?.let { bitmap ->
                            DogImage(bitmap = bitmap)
                        }
                    }
                }

            }

        }

        Button(onClick = {
            viewModel.clearRecentlyGeneratedDogs()
        }) {
            Text(text = stringResource(id = R.string.clear))
        }
    }

}

@Composable
fun DogImage(bitmap: Bitmap) {
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
}