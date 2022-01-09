package com.patofch.rickandmorty.data.data_source.db.model

import com.patofch.rickandmorty.domain.model.Character
import com.patofch.rickandmorty.domain.model.CharacterEntityMapper

internal class CharacterDtoEntityMapperImpl : CharacterEntityMapper<CharacterDtoEntity> {

    override fun mapToCharacter(entity: CharacterDtoEntity): Character {
        return entity.run {
            Character(
                id = id,
                image = image,
                name = name,
                species = species,
                status = status
            )
        }
    }
}