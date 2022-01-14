package com.patofch.rickandmorty.data.api.model

import com.patofch.rickandmorty.data.data_source.api.model.CharacterApiEntityMapper
import com.patofch.rickandmorty.data.utils.getFakeCharacterApi
import com.patofch.rickandmorty.data.utils.getFakeCharacterDto
import org.junit.Before
import org.junit.Test

class CharacterApiEntityMapperTest {

    private lateinit var characterApiEntityMapper: CharacterApiEntityMapper

    private val fakeCharacterApi = getFakeCharacterApi()

    private val fakeCharacterDto = getFakeCharacterDto()

    @Before
    fun setUp() {
        characterApiEntityMapper = CharacterApiEntityMapper()
    }

    @Test
    fun `verify mapToCharacterDtoEntity returns expected characterDto`() {
        val characterDto = characterApiEntityMapper.mapToCharacterDtoEntity(fakeCharacterApi)

        assert(characterDto == fakeCharacterDto)
    }
}