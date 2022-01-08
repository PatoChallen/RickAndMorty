package com.patofch.rickandmorty.feature_character.domain.model

data class Character(
    val id: Int? = null,
    val name: String,
    val image: String,
    val status: String? = null,
    val species: String? = null,
)