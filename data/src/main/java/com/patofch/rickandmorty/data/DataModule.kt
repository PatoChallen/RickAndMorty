package com.patofch.rickandmorty.data

import com.patofch.rickandmorty.data.data_source.api.CharacterService
import com.patofch.rickandmorty.data.data_source.api.NetworkController
import com.patofch.rickandmorty.data.data_source.api.NetworkModule
import com.patofch.rickandmorty.data.data_source.api.model.CharacterApiEntityMapper
import com.patofch.rickandmorty.data.data_source.db.CharacterDao
import com.patofch.rickandmorty.data.data_source.db.DatabaseModule
import com.patofch.rickandmorty.data.data_source.db.RickAndMortyDatabase
import com.patofch.rickandmorty.data.data_source.db.model.CharacterDtoEntity
import com.patofch.rickandmorty.data.data_source.db.model.CharacterDtoEntityMapperImpl
import com.patofch.rickandmorty.data.repository.CharacterRepositoryImpl
import com.patofch.rickandmorty.domain.model.CharacterEntityMapper
import com.patofch.rickandmorty.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class
    ]
)
@DisableInstallInCheck
object DataModule {

    @Provides
    @Singleton
    internal fun provideCharacterService(retrofit: Retrofit): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideCharacterDao(database: RickAndMortyDatabase): CharacterDao {
        return database.characterDao
    }

    @Provides
    @Singleton
    internal fun provideCharacterDtoEntityMapper(): CharacterEntityMapper<CharacterDtoEntity> {
        return CharacterDtoEntityMapperImpl()
    }

    @Provides
    @Singleton
    internal fun provideCharacterApiEntityMapper(): CharacterApiEntityMapper {
        return CharacterApiEntityMapper()
    }

    @Provides
    @Singleton
    internal fun provideCharacterRepository(
        characterService: CharacterService,
        characterDao: CharacterDao,
        dtoEntityMapper: CharacterEntityMapper<CharacterDtoEntity>,
        apiEntityMapper: CharacterApiEntityMapper,
        networkController: NetworkController
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            characterService = characterService,
            characterDao = characterDao,
            dtoEntityMapper = dtoEntityMapper,
            apiEntityMapper = apiEntityMapper,
            networkController = networkController
        )
    }
}