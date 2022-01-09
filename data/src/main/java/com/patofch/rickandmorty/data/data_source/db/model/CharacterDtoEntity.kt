package com.patofch.rickandmorty.data.data_source.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class CharacterDtoEntity(
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