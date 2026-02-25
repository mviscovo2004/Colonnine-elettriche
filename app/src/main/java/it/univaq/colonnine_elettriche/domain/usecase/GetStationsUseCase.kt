package it.univaq.colonnine_elettriche.domain.usecase

import it.univaq.colonnine_elettriche.domain.model.Station
import it.univaq.colonnine_elettriche.domain.repositories.LocalRepository
import it.univaq.colonnine_elettriche.domain.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStationsUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(): Flow<Result<List<Station>>> = flow {
        // 1. Emetti prima i dati locali per velocità
        val localData = localRepository.getAll()
        if (localData.isNotEmpty()) {
            emit(Result.success(localData))
        }

        // 2. Tenta il download remoto
        try {
            val remoteData = remoteRepository.downloadData()
            if (remoteData.isNotEmpty()) {
                localRepository.clear()
                localRepository.save(remoteData)
                emit(Result.success(remoteData))
            }
        } catch (e: Exception) {
            // Se i dati locali erano già stati emessi, non emettiamo l'errore se non strettamente necessario
            if (localData.isEmpty()) {
                emit(Result.failure(e))
            }
        }
    }
}
