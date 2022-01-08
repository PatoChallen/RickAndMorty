package com.patofch.rickandmorty.feature_character.domain.model

interface CharacterEntityMapper<Entity>  {

    fun mapToCharacter(entity: Entity): Character
}