package com.patofch.rickandmorty.domain.use_case

import com.patofch.rickandmorty.domain.model.Character
import com.patofch.rickandmorty.domain.repository.CharacterRepository

class GetCharacterById(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(
        id: Int
    ): Character? = characterRepository.getCharacterById(id)
}