package com.patofch.rickandmorty.core.di

import android.app.Application
import android.content.Context
import com.patofch.rickandmorty.feature_character.core.di.CharacterModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class,
        CharacterModule::class
    ]
)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app
    }
}