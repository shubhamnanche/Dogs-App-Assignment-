package com.svg.dogsapp.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val recentlyGeneratedDogs = viewModel.recentlyGeneratedDogs.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRecentlyGeneratedDogs()
        Log.d(TAG, "RecentDogsScreen: size > ${recentlyGeneratedDogs.value.size}")
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
            if (recentlyGeneratedDogs.value.isEmpty()) {
                item {
                    Text(text = stringResource(id = R.string.no_recently_generated_dogs))
                }
            } else {
                items(recentlyGeneratedDogs.value.size) {
                    viewModel.loadImageBitmap(recentlyGeneratedDogs.value[it])?.let { bitmap ->
                        DogImage(bitmap = bitmap)
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
            .padding(16.dp)
            .requiredWidth(200.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
    )
}