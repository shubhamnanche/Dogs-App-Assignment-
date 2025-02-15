package com.svg.dogsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        navigation(
                            startDestination = "main_screen",
                            route = "home"
                        ) {
                            composable("main_screen") {
                                val viewModel = it.sharedViewModel<DogImageViewModel>(navController = navController)
                                MainScreen(
                                    innerPadding = innerPadding,
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }
                            composable("generate_dog_screen") {
                                val viewModel = it.sharedViewModel<DogImageViewModel>(navController = navController)
                                GenerateDogScreen(
                                    innerPadding = innerPadding,
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }
                            composable("recently_generated_dogs_screen") {
                                val viewModel = it.sharedViewModel<DogImageViewModel>(navController = navController)
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

