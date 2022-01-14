package com.patofch.rickandmorty.domain.use_case

import com.nhaarman.mockitokotlin2.verify
import com.patofch.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetCharactersTest {

    @Mock
    private lateinit var characterRepository: CharacterRepository

    private lateinit var getCharacters: GetCharacters

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCharacters = GetCharacters(characterRepository)
    }

    @Test
    fun `verify getCharacters invoke calls characterRepository getCharacters`() {
        runBlocking {
            getCharacters()

            verify(characterRepository).getCharacters()
        }
    }
}