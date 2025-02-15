package com.svg.dogsapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.svg.dogsapp.R
import com.svg.dogsapp.ui.theme.ButtonColor
import com.svg.dogsapp.ui.theme.ButtonColorDark

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
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = stringResource(id = R.string.random_dog_generator))

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {
                navController.navigate("generate_dog_screen")
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
            Text(text = stringResource(id = R.string.generate_dog))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                navController.navigate("recently_generated_dogs_screen")
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
            Text(text = stringResource(id = R.string.recently_generated_dogs))
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}