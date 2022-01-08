package com.patofch.rickandmorty.feature_character.domain.use_case

import com.patofch.rickandmorty.feature_character.domain.repository.CharacterRepository

class LoadMoreCharacters(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke() = characterRepository.loadMoreCharacters()
}