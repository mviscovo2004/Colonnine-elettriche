package it.univaq.colonnine_elettriche.data.remote.service

import it.univaq.colonnine_elettriche.data.remote.model.RemoteStation
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPointService {
    @GET("poi/")
    suspend fun stations(
        @Query("output") output: String = "json",
        @Query("countrycode") countryCode: String = "IT",
        @Query("maxresults") maxResults: Int = 1000,
        @Query("compact") compact: Boolean = true,
        @Query("verbose") verbose: Boolean = false
    ): List<RemoteStation>
}
