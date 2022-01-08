package com.patofch.rickandmorty.feature_character.data.data_source.db.model

import com.patofch.rickandmorty.feature_character.domain.model.Character
import com.patofch.rickandmorty.feature_character.domain.model.CharacterEntityMapper

class CharacterDtoEntityMapperImpl : CharacterEntityMapper<CharacterDtoEntity> {

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