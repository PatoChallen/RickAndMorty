package com.patofch.rickandmorty.core.di

import android.app.Application
import androidx.room.Room
import com.patofch.rickandmorty.core.data.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(app: Application): RickAndMortyDatabase {
        return Room.databaseBuilder(
            app,
            RickAndMortyDatabase::class.java,
            RickAndMortyDatabase.DATABASE_NAME
        ).build()
    }
}