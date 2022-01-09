package com.patofch.rickandmorty.data.data_source.api.model

internal data class CharacterApiResponse(
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
