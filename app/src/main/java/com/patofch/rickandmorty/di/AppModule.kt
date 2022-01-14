package com.patofch.rickandmorty.di

import android.app.Application
import android.content.Context
import com.patofch.rickandmorty.data.DataModule
import com.patofch.rickandmorty.domain.DomainModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        DataModule::class,
        DomainModule::class
    ]
)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app
    }
}