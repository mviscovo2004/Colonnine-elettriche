package it.univaq.colonnine_elettriche.data.local

import it.univaq.colonnine_elettriche.data.local.dao.StationDao
import it.univaq.colonnine_elettriche.data.local.entities.StationEntity
import it.univaq.colonnine_elettriche.domain.model.Station
import it.univaq.colonnine_elettriche.domain.repositories.LocalRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private fun Station.toEntity() =
    StationEntity(
        id = id,
        title = title,
        address = address,
        lat = lat,
        lng = lng
    )

private fun StationEntity.toDomain() =
    Station(
        id = id,
        title = title,
        address = address,
        lat = lat,
        lng = lng
    )

class RoomLocalRepository @Inject constructor(private val stationDao: StationDao) : LocalRepository {

    override suspend fun save(data: List<Station>) {
        withContext(Dispatchers.IO) {
            stationDao.insertAll(data.map { it.toEntity() })
        }
    }

    override suspend fun getAll(): List<Station> {
        return stationDao.getAll().map { it.toDomain() }
    }

    override suspend fun clear() {
        stationDao.clear()
    }
}
