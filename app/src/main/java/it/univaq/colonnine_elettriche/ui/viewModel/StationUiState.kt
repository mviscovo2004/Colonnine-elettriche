package it.univaq.colonnine_elettriche.ui.viewModel

import it.univaq.colonnine_elettriche.domain.model.Station

sealed class StationUiState {
    object Loading : StationUiState()
    data class Success(val stations: List<Station>) : StationUiState()
    data class Error(val message: String) : StationUiState()
}
