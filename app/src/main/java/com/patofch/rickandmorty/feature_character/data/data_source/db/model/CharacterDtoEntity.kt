package com.patofch.rickandmorty.feature_character.data.data_source.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterDtoEntity(
    @PrimaryKey val id: Int?,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val locationName: String,
    val locationUrl: String,
    val name: String,
    val originName: String,
    val originUrl: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)