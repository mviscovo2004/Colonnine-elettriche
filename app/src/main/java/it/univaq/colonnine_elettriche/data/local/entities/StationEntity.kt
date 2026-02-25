package it.univaq.colonnine_elettriche.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val address: String,
    val lat: Double,
    val lng: Double
)
