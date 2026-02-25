package it.univaq.colonnine_elettriche.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import it.univaq.colonnine_elettriche.data.local.dao.StationDao
import it.univaq.colonnine_elettriche.data.local.entities.StationEntity

@Database(entities = [StationEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao
}
