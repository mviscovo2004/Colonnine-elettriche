package it.univaq.colonnine_elettriche.domain.repositories

import it.univaq.colonnine_elettriche.domain.model.Station

interface RemoteRepository {
    suspend fun downloadData(): List<Station>
}
