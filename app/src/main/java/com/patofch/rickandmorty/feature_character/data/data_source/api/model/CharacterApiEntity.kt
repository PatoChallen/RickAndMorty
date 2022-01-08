package com.patofch.rickandmorty.feature_character.data.data_source.api.model

data class CharacterApiEntity(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: EntitySummary,
    val name: String,
    val origin: EntitySummary,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    data class EntitySummary(
        val name: String,
        val url: String
    )
}