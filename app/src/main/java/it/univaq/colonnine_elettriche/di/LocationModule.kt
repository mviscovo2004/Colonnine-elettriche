package it.univaq.colonnine_elettriche.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.univaq.colonnine_elettriche.ui.screens.common.LocationHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    @Singleton
    fun provideLocationHelper(@ApplicationContext context: Context) = LocationHelper(context)
}
