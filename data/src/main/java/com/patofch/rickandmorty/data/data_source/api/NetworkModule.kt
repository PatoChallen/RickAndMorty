package com.patofch.rickandmorty.data.data_source.api

import android.content.Context
import com.patofch.rickandmorty.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkController(context: Context): NetworkController {
        return NetworkControllerImpl(context)
    }
}