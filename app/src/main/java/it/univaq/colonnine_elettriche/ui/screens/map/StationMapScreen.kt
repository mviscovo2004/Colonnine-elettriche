package it.univaq.colonnine_elettriche.ui.screens.map

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import it.univaq.colonnine_elettriche.ui.screens.common.LocationHelper
import it.univaq.colonnine_elettriche.ui.screens.common.PermissionGate
import it.univaq.colonnine_elettriche.ui.viewModel.StationUiState
import it.univaq.colonnine_elettriche.ui.viewModel.StationViewModel

@Composable
fun StationMapScreen(viewModel: StationViewModel, onStationClick: (Long) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    
    PermissionGate(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ) {
        val context = LocalContext.current
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(41.8719, 12.5674), 6f)
        }

        val locationHelper = remember { LocationHelper(context) }
        var hasCenteredMap by remember { mutableStateOf(false) }

        val locationCallback = remember {
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let { location ->
                        if (!hasCenteredMap) {
                            val userLatLng = LatLng(location.latitude, location.longitude)
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(userLatLng, 13f)
                            hasCenteredMap = true
                        }
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            locationHelper.start(locationCallback)
            onDispose { locationHelper.stop(locationCallback) }
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
                        properties = MapProperties(
                            isMyLocationEnabled = true,
                            mapType = MapType.NORMAL // Forziamo il tipo di mappa normale
                        ),
                        uiSettings = MapUiSettings(
                            myLocationButtonEnabled = true,
                            zoomControlsEnabled = true,
                            mapToolbarEnabled = true
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
}
