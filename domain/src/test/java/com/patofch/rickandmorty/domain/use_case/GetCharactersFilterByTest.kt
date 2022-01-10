package com.patofch.rickandmorty.domain.use_case

import com.nhaarman.mockitokotlin2.verify
import com.patofch.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetCharactersFilterByTest {

    @Mock
    private lateinit var characterRepository: CharacterRepository

    private lateinit var getCharactersFilterBy: GetCharactersFilterBy

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCharactersFilterBy = GetCharactersFilterBy(characterRepository)
    }

    @Test
    fun `verify getCharacters invoke calls characterRepository getCharacters`() {
        val fakeName = "fakeName"
        val fakeStatus = "fakeStatus"
        val fakeSpecies = "fakeSpecies"
        val fakeType = "fakeType"
        val fakeGender = "fakeGender"

        runBlocking {
            getCharactersFilterBy(
                name = fakeName,
                status = fakeStatus,
                species = fakeSpecies,
                type = fakeType,
                gender = fakeGender
            )

            verify(characterRepository).getCharactersFilterBy(
                name = fakeName,
                status = fakeStatus,
                species = fakeSpecies,
                type = fakeType,
                gender = fakeGender
            )
        }
    }
}