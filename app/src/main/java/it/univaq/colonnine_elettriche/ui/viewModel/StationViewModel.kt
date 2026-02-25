package it.univaq.colonnine_elettriche.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.univaq.colonnine_elettriche.domain.usecase.GetStationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getStationsUseCase: GetStationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<StationUiState>(StationUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _uiState.value = StationUiState.Loading
            getStationsUseCase().collect { result ->
                result.onSuccess { stations ->
                    _uiState.value = StationUiState.Success(stations)
                }
                result.onFailure { error ->
                    _uiState.value = StationUiState.Error(error.message ?: "Errore sconosciuto")
                }
            }
        }
    }
}
