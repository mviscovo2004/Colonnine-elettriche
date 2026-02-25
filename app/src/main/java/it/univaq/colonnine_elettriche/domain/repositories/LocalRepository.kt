package it.univaq.colonnine_elettriche.domain.repositories

import it.univaq.colonnine_elettriche.domain.model.Station

interface LocalRepository {
    suspend fun save(data: List<Station>)
    suspend fun getAll(): List<Station>
    suspend fun clear()
}
