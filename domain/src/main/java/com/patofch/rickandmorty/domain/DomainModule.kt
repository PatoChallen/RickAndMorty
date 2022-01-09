package com.patofch.rickandmorty.domain

import com.patofch.rickandmorty.domain.repository.CharacterRepository
import com.patofch.rickandmorty.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
object DomainModule {

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