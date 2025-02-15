package com.svg.dogsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.svg.dogsapp.presentation.DogImageViewModel
import com.svg.dogsapp.presentation.GenerateDogScreen
import com.svg.dogsapp.presentation.MainScreen
import com.svg.dogsapp.presentation.RecentlyGeneratedDogsScreen
import com.svg.dogsapp.ui.theme.DogsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogsAppTheme {
                val navController = rememberNavController()
                var showBackButton by remember { mutableStateOf(false) }
                var currentScreenTitle by remember { mutableStateOf(getString(R.string.app_name)) }

                navController.addOnDestinationChangedListener { controller, destination, arguments ->
                    showBackButton = destination.route != "main_screen"

                    currentScreenTitle = when (destination.route) {
                        "main_screen" -> getString(R.string.app_name)
                        "generate_dog_screen" -> getString(R.string.generate_dog)
                        "recently_generated_dogs_screen" -> getString(R.string.recently_generated_dogs)
                        else -> getString(R.string.app_name)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MediumTopAppBar(
                            title = {
                                Text(
                                    text = currentScreenTitle,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            },
                            navigationIcon = {
                                if (showBackButton) {
                                    TextButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = stringResource(id = R.string.back)
                                        )
                                        Text(text = stringResource(id = R.string.back))
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home") {
                        navigation(
                            startDestination = "main_screen",
                            route = "home"
                        ) {
                            composable("main_screen") {
                                val viewModel =
                                    it.sharedViewModel<DogImageViewModel>(navController = navController)
                                MainScreen(
                                    innerPadding = innerPadding,
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }
                            composable("generate_dog_screen") {
                                val viewModel =
                                    it.sharedViewModel<DogImageViewModel>(navController = navController)
                                GenerateDogScreen(
                                    innerPadding = innerPadding,
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }
                            composable("recently_generated_dogs_screen") {
                                val viewModel =
                                    it.sharedViewModel<DogImageViewModel>(navController = navController)
                                RecentlyGeneratedDogsScreen(
                                    innerPadding = innerPadding,
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

