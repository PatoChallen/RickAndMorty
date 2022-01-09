package com.patofch.rickandmorty.domain.model

interface CharacterEntityMapper<Entity>  {

    fun mapToCharacter(entity: Entity): Character
}