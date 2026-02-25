package it.univaq.colonnine_elettriche.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import it.univaq.colonnine_elettriche.ui.viewModel.StationViewModel
import it.univaq.colonnine_elettriche.ui.viewModel.StationUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDetailScreen(stationId: Long, viewModel: StationViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dettaglio Colonnina") })
        }
    ) { padding ->
        when (val state = uiState) {
            is StationUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is StationUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Errore: ${state.message}")
                }
            }
            is StationUiState.Success -> {
                val station = state.stations.find { it.id == stationId }
                if (station != null) {
                    Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                        Text(text = station.title, style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = station.address, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Posizione:", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Lat: ${station.lat}")
                        Text(text = "Lng: ${station.lng}")
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Colonnina non trovata")
                    }
                }
            }
        }
    }
}
