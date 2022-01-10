package com.patofch.rickandmorty.domain.use_case

import com.nhaarman.mockitokotlin2.verify
import com.patofch.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoadMoreCharactersTest {

    @Mock
    private lateinit var characterRepository: CharacterRepository

    private lateinit var loadMoreCharacters: LoadMoreCharacters

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadMoreCharacters = LoadMoreCharacters(characterRepository)
    }

    @Test
    fun `verify getCharacters invoke calls characterRepository getCharacters`() {
        runBlocking {
            loadMoreCharacters()

            verify(characterRepository).loadMoreCharacters()
        }
    }
}