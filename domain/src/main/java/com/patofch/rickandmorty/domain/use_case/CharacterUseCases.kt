package com.patofch.rickandmorty.domain.use_case

import javax.inject.Inject

data class CharacterUseCases @Inject constructor(
    val getCharacters: GetCharacters,
    val getCharacterById: GetCharacterById,
    val getCharactersFilterBy: GetCharactersFilterBy,
    val loadMoreCharacters: LoadMoreCharacters
)
