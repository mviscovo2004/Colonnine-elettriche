package it.univaq.progettofinale

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import it.univaq.colonnine_elettriche.ui.screens.StationDetailScreen
import it.univaq.colonnine_elettriche.ui.screens.StationListScreen
import it.univaq.colonnine_elettriche.ui.screens.StationMapScreen
import it.univaq.colonnine_elettriche.ui.theme.ProgettoFinaleTheme
import it.univaq.colonnine_elettriche.ui.viewModel.StationViewModel
import kotlinx.serialization.Serializable

@Serializable
data object ListScreen

@Serializable
data class DetailScreen(val id: Long)

@Serializable
data object MapScreen

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgettoFinaleTheme {
                val navController = rememberNavController()
                val viewModel: StationViewModel = hiltViewModel()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val permissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { }

                LaunchedEffect(Unit) {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                val title = when {
                                    currentDestination?.hasRoute<ListScreen>() == true -> "Lista Colonnine"
                                    currentDestination?.hasRoute<MapScreen>() == true -> "Mappa Colonnine"
                                    currentDestination?.hasRoute<DetailScreen>() == true -> "Dettaglio"
                                    else -> "Progetto Finale"
                                }
                                Text(title)
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Lista") },
                                label = { Text("Lista") },
                                // Consideriamo selezionato "Lista" anche se siamo nel dettaglio
                                selected = currentDestination?.hasRoute<ListScreen>() == true || currentDestination?.hasRoute<DetailScreen>() == true,
                                onClick = {
                                    // Se siamo nel dettaglio, torniamo indietro alla lista
                                    if (currentDestination?.hasRoute<DetailScreen>() == true) {
                                        navController.popBackStack(ListScreen, inclusive = false)
                                    } else if (currentDestination?.hasRoute<ListScreen>() != true) {
                                        navController.navigate(ListScreen) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Place, contentDescription = "Mappa") },
                                label = { Text("Mappa") },
                                selected = currentDestination?.hasRoute<MapScreen>() == true,
                                onClick = {
                                    if (currentDestination?.hasRoute<MapScreen>() != true) {
                                        navController.navigate(MapScreen) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = ListScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<ListScreen> {
                            StationListScreen(
                                viewModel = viewModel,
                                onStationClick = { id -> navController.navigate(DetailScreen(id)) }
                            )
                        }
                        composable<MapScreen> {
                            StationMapScreen(
                                viewModel = viewModel,
                                onStationClick = { id -> navController.navigate(DetailScreen(id)) }
                            )
                        }
                        composable<DetailScreen> { backStackEntry ->
                            val detail: DetailScreen = backStackEntry.toRoute()
                            StationDetailScreen(stationId = detail.id, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
