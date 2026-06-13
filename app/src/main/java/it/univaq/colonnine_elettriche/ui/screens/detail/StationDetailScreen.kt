package it.univaq.colonnine_elettriche.ui.screens.detail

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
    
    // Rimuoviamo Scaffold interno se lo screen è già dentro uno Scaffold in MainActivity
    // In questo caso, MainActivity ha già uno Scaffold con TopAppBar e BottomBar.
    // Ma StationDetailScreen viene navigato dentro il NavHost.
    
    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is StationUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is StationUiState.Error -> {
                Text(
                    text = "Errore: ${state.message}",
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
            is StationUiState.Success -> {
                val station = state.stations.find { it.id == stationId }
                if (station != null) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = station.title, style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = station.address, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Posizione:", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Lat: ${station.lat}")
                        Text(text = "Lng: ${station.lng}")
                    }
                } else {
                    Text(
                        text = "Colonnina non trovata",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
