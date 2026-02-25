package it.univaq.colonnine_elettriche.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import it.univaq.colonnine_elettriche.domain.model.Station
import it.univaq.colonnine_elettriche.ui.viewModel.StationUiState
import it.univaq.colonnine_elettriche.ui.viewModel.StationViewModel

@Composable
fun StationListScreen(
    viewModel: StationViewModel,
    onStationClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is StationUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is StationUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp) // Aggiunto padding
                ) {
                    items(state.stations) { station ->
                        StationItem(station = station, onClick = { onStationClick(station.id) })
                    }
                }
            }
            is StationUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationItem(station: Station, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // Aggiunto padding verticale
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp) // Riduci l'elevazione
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = station.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp)) // Riduci lo spazio
            Text(text = station.address, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
