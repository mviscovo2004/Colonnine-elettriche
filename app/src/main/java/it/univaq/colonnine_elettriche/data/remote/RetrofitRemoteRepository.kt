package it.univaq.colonnine_elettriche.data.remote

import it.univaq.colonnine_elettriche.data.remote.model.RemoteStation
import it.univaq.colonnine_elettriche.data.remote.service.EndPointService
import it.univaq.colonnine_elettriche.domain.model.Station
import it.univaq.colonnine_elettriche.domain.repositories.RemoteRepository
import javax.inject.Inject

private fun RemoteStation.toDomain() =
    Station(
        id = id,
        title = addressInfo.title,
        address = addressInfo.addressLine1,
        lat = addressInfo.latitude,
        lng = addressInfo.longitude
    )

class RetrofitRemoteRepository @Inject constructor(private val service: EndPointService) :
    RemoteRepository {

    override suspend fun downloadData(): List<Station> {
        return service.stations().map { it.toDomain() }
    }
}
