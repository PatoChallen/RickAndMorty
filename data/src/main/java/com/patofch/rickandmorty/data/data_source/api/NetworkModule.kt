package com.patofch.rickandmorty.data.data_source.api

import android.content.Context
import android.net.ConnectivityManager
import com.patofch.rickandmorty.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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
    @Named("NetworkState")
    fun provideNetworkState(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}