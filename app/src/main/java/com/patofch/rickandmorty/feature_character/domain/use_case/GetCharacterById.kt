package com.patofch.rickandmorty.feature_character.domain.use_case

import com.patofch.rickandmorty.feature_character.domain.model.Character
import com.patofch.rickandmorty.feature_character.domain.repository.CharacterRepository

class GetCharacterById(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(
        id: Int
    ): Character = characterRepository.getCharacterById(id)
}