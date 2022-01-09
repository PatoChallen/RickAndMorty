package com.patofch.rickandmorty.domain.use_case

import com.patofch.rickandmorty.domain.repository.CharacterRepository

class LoadMoreCharacters(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke() = characterRepository.loadMoreCharacters()
}