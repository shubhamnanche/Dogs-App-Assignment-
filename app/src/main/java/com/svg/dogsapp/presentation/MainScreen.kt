package com.svg.dogsapp.presentation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.svg.dogsapp.R

@Composable
fun MainScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: DogImageViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .scrollable(
                state = rememberScrollState(0),
                orientation = Orientation.Vertical
            )
    ) {
        Button(onClick = {
            navController.navigate("generate_dog_screen")
        }) {
            Text(text = stringResource(id = R.string.generate_dog))
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = {
            navController.navigate("recently_generated_dogs_screen")
        }) {
            Text(text = stringResource(id = R.string.recently_generated_dogs))
        }
    }
}