package it.univaq.colonnine_elettriche.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteStation(
    @SerializedName("ID") val id: Long,
    @SerializedName("AddressInfo") val addressInfo: AddressInfo
)

data class AddressInfo(
    @SerializedName("Title") val title: String,
    @SerializedName("AddressLine1") val addressLine1: String,
    @SerializedName("Town") val town: String?,
    @SerializedName("Latitude") val latitude: Double,
    @SerializedName("Longitude") val longitude: Double
)
