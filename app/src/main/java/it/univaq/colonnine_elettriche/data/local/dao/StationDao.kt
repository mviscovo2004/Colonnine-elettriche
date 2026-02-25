package it.univaq.colonnine_elettriche.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import it.univaq.colonnine_elettriche.data.local.entities.StationEntity

@Dao
interface StationDao {
    @Upsert suspend fun insertAll(stations: List<StationEntity>)

    @Query("SELECT * FROM stations") suspend fun getAll(): List<StationEntity>

    @Query("DELETE FROM stations") suspend fun clear()
}
