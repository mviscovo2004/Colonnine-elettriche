package it.univaq.colonnine_elettriche.domain.model

data class Station(
    val id: Long,
    val title: String,
    val address: String,
    val lat: Double,
    val lng: Double
)
