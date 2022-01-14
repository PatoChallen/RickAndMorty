package com.patofch.rickandmorty.data.data_source.api.model

import com.patofch.rickandmorty.data.data_source.db.model.CharacterDtoEntity

internal class CharacterApiEntityMapper {

    fun mapToCharacterDtoEntity(entity: CharacterApiEntity): CharacterDtoEntity {
        return entity.run {
            CharacterDtoEntity(
                id = id,
                image = image,
                name = name,
                status = status,
                created = created,
                episode = episode,
                species = species,
                gender = gender,
                locationName = location.name,
                locationUrl = location.url,
                originName = origin.name,
                originUrl = origin.url,
                type = type,
                url = url
            )
        }
    }
}