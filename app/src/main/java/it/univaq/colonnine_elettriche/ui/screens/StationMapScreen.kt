package it.univaq.colonnine_elettriche.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import it.univaq.colonnine_elettriche.ui.viewModel.StationUiState
import it.univaq.colonnine_elettriche.ui.viewModel.StationViewModel

@SuppressLint("MissingPermission")
@Composable
fun StationMapScreen(viewModel: StationViewModel, onStationClick: (Long) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    
    val defaultPos = LatLng(41.8719, 12.5674)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPos, 6f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is StationUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is StationUiState.Success -> {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = true),
                    uiSettings = MapUiSettings(
                        myLocationButtonEnabled = true,
                        zoomControlsEnabled = true
                    )
                ) {
                    state.stations.forEach { station ->
                        Marker(
                            state = MarkerState(position = LatLng(station.lat, station.lng)),
                            title = station.title,
                            snippet = station.address,
                            onClick = {
                                onStationClick(station.id)
                                false
                            }
                        )
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
