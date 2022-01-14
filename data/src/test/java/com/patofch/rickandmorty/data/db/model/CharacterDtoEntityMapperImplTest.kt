package com.patofch.rickandmorty.data.db.model

import com.patofch.rickandmorty.data.data_source.db.model.CharacterDtoEntity
import com.patofch.rickandmorty.data.data_source.db.model.CharacterDtoEntityMapperImpl
import com.patofch.rickandmorty.data.utils.getFakeCharacter
import com.patofch.rickandmorty.data.utils.getFakeCharacterDto
import com.patofch.rickandmorty.domain.model.Character
import com.patofch.rickandmorty.domain.model.CharacterEntityMapper
import org.junit.Before
import org.junit.Test

class CharacterDtoEntityMapperImplTest {

    private lateinit var characterDtoEntityMapper: CharacterEntityMapper<CharacterDtoEntity>

    private val fakeCharacter = getFakeCharacter()

    private val fakeCharacterDto = getFakeCharacterDto()

    @Before
    fun setUp() {
        characterDtoEntityMapper = CharacterDtoEntityMapperImpl()
    }

    @Test
    fun `verify mapToCharacterDtoEntity returns expected characterDto`() {
        val character = characterDtoEntityMapper.mapToCharacter(fakeCharacterDto)

        println(character)
        assert(character == fakeCharacter)
    }
}