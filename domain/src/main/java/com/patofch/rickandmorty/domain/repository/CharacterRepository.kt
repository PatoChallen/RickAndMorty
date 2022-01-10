package com.patofch.rickandmorty.domain.repository

import com.patofch.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacters(): Flow<List<Character>>

    suspend fun getCharacterById(id: Int): Character?

    suspend fun loadMoreCharacters()

    suspend fun getCharactersFilterBy(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Flow<List<Character>>
}