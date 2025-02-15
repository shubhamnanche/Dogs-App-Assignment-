package com.svg.dogsapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.svg.dogsapp.R
import com.svg.dogsapp.ui.theme.ButtonColor
import com.svg.dogsapp.ui.theme.ButtonColorDark

@Composable
fun GenerateDogScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: DogImageViewModel
) {
    val result = viewModel.dogImage.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        AsyncImage(
            model = result.data?.url,
            contentDescription = "Dog Image",
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_error),
            fallback = painterResource(id = R.mipmap.ic_launcher_monochrome),
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

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                viewModel.getRandomDogImage()
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
            Text(text = stringResource(id = R.string.generate))
        }

        Spacer(modifier = Modifier.height(20.dp))

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

}


