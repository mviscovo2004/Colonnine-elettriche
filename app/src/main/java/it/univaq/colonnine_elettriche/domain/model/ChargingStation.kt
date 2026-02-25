package it.univaq.colonnine_elettriche.domain.model

data class ChargingStation(
    val id: Long,
    val title: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val usageType: String = "Public"
)
