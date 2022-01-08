package com.patofch.rickandmorty.feature_character.data.data_source.api.model

data class CharacterApiResponse(
    val info: ResponseInfo,
    val results: List<CharacterApiEntity>
) {

    data class ResponseInfo(
        val count: Int,
        val next: String?,
        val pages: Int,
        val prev: String?
    )
}
