package com.patofch.rickandmorty.data.utils

import com.patofch.rickandmorty.data.data_source.api.model.CharacterApiEntity
import com.patofch.rickandmorty.data.data_source.db.model.CharacterDtoEntity
import com.patofch.rickandmorty.domain.model.Character


internal fun getFakeCharacterDto() = CharacterDtoEntity(
    id = 1,
    created = "fake date",
    episode = listOf("fake episode"),
    gender = "fake gender",
    locationName = "fake location name",
    image = "fake image",
    locationUrl = "fake location url",
    name = "fake name",
    originName = "fake origin name",
    originUrl = "fake origin url",
    species = "fake species",
    status = "fake status",
    type = "fake type",
    url = "fake url"
)

internal fun getFakeCharacterApi() = CharacterApiEntity(
    id = 1,
    created = "fake date",
    episode = listOf("fake episode"),
    gender = "fake gender",
    location = CharacterApiEntity.EntitySummary("fake location name", "fake location url"),
    image = "fake image",
    name = "fake name",
    origin = CharacterApiEntity.EntitySummary("fake origin name", "fake origin url"),
    species = "fake species",
    status = "fake status",
    type = "fake type",
    url = "fake url"
)

fun getFakeCharacter() = Character(
    id = 1,
    name = "fake name",
    image = "fake image",
    status = "fake status",
    species = "fake species"
)