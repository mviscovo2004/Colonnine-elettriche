package it.univaq.colonnine_elettriche.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.univaq.colonnine_elettriche.data.local.RoomLocalRepository
import it.univaq.colonnine_elettriche.data.remote.RetrofitRemoteRepository
import it.univaq.colonnine_elettriche.domain.repositories.LocalRepository
import it.univaq.colonnine_elettriche.domain.repositories.RemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocalRepository(
        repository: RoomLocalRepository
    ): LocalRepository

    @Binds
    @Singleton
    abstract fun bindRemoteRepository(
        repository: RetrofitRemoteRepository
    ): RemoteRepository
}
