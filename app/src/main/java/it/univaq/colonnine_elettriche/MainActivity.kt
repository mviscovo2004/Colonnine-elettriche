// File: C:/Users/Marco/Desktop/Colonnine_Elettriche/app/src/main/java/it/univaq/colonnine_elettriche/MainActivity.kt
package it.univaq.colonnine_elettriche

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import it.univaq.colonnine_elettriche.ui.screens.detail.StationDetailScreen
import it.univaq.colonnine_elettriche.ui.screens.list.StationListScreen
import it.univaq.colonnine_elettriche.ui.screens.map.StationMapScreen
import it.univaq.colonnine_elettriche.ui.theme.ProgettoFinaleTheme
import it.univaq.colonnine_elettriche.ui.viewModel.StationViewModel
import kotlinx.serialization.Serializable

@Serializable data object ListScreen
@Serializable data class DetailScreen(val id: Long)
@Serializable data object MapScreen

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
                                icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                                label = { Text("Lista") },
                                selected = currentDestination?.hasRoute<ListScreen>() == true || currentDestination?.hasRoute<DetailScreen>() == true,
                                onClick = {
                                    if (currentDestination?.hasRoute<DetailScreen>() == true) {
                                        navController.popBackStack(ListScreen, false)
                                    } else {
                                        navController.navigate(ListScreen) {
                                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Place, null) },
                                label = { Text("Mappa") },
                                selected = currentDestination?.hasRoute<MapScreen>() == true,
                                onClick = {
                                    navController.navigate(MapScreen) {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController, ListScreen, Modifier.padding(innerPadding)) {
                        composable<ListScreen> { StationListScreen(viewModel) { id -> navController.navigate(DetailScreen(id)) } }
                        composable<MapScreen> { StationMapScreen(viewModel) { id -> navController.navigate(DetailScreen(id)) } }
                        composable<DetailScreen> { backStackEntry ->
                            val detail: DetailScreen = backStackEntry.toRoute()
                            StationDetailScreen(detail.id, viewModel)
                        }
                    }
                }
            }
        }
    }
}