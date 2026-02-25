package it.univaq.colonnine_elettriche.data.remote.model

data class RemoteAddress(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: RemoteGeo
)

data class RemoteGeo(val lat: String, val lng: String)

data class RemoteCompany(val name: String, val catchPhrase: String, val bs: String)
