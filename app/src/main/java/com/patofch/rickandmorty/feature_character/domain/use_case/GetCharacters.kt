package com.patofch.rickandmorty.feature_character.domain.use_case

import com.patofch.rickandmorty.feature_character.domain.model.Character
import com.patofch.rickandmorty.feature_character.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharacters(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(): Flow<List<Character>> = characterRepository.getCharacters()
}