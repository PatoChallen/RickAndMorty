package com.patofch.rickandmorty.feature_character.core.di

import com.patofch.rickandmorty.core.data.RickAndMortyDatabase
import com.patofch.rickandmorty.feature_character.data.data_source.api.CharacterService
import com.patofch.rickandmorty.feature_character.data.data_source.api.model.CharacterApiEntityMapper
import com.patofch.rickandmorty.feature_character.data.data_source.db.CharacterDao
import com.patofch.rickandmorty.feature_character.data.data_source.db.model.CharacterDtoEntity
import com.patofch.rickandmorty.feature_character.data.data_source.db.model.CharacterDtoEntityMapperImpl
import com.patofch.rickandmorty.feature_character.data.repository.CharacterRepositoryImpl
import com.patofch.rickandmorty.feature_character.domain.model.CharacterEntityMapper
import com.patofch.rickandmorty.feature_character.domain.repository.CharacterRepository
import com.patofch.rickandmorty.feature_character.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterModule {

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: RickAndMortyDatabase): CharacterDao {
        return database.characterDao
    }

    @Provides
    @Singleton
    fun provideCharacterDtoEntityMapper(): CharacterEntityMapper<CharacterDtoEntity> {
        return CharacterDtoEntityMapperImpl()
    }

    @Provides
    @Singleton
    fun provideCharacterApiEntityMapper(): CharacterApiEntityMapper {
        return CharacterApiEntityMapper()
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterService: CharacterService,
        characterDao: CharacterDao,
        dtoEntityMapper: CharacterEntityMapper<CharacterDtoEntity>,
        apiEntityMapper: CharacterApiEntityMapper,
        @Named("NetworkState") networkConnected: Boolean
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            characterService = characterService,
            characterDao = characterDao,
            dtoEntityMapper = dtoEntityMapper,
            apiEntityMapper = apiEntityMapper,
            networkConnected = networkConnected
        )
    }

    @Provides
    @Singleton
    fun provideCharacterUseCases(
        characterRepository: CharacterRepository
    ): CharacterUseCases {
        return CharacterUseCases(
            getCharacters = GetCharacters(characterRepository),
            getCharacterById = GetCharacterById(characterRepository),
            getCharactersFilterBy = GetCharactersFilterBy(characterRepository),
            loadMoreCharacters = LoadMoreCharacters(characterRepository)
        )
    }
}