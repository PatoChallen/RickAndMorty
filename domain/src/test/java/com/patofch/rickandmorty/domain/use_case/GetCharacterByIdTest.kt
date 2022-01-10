package com.patofch.rickandmorty.domain.use_case

import com.google.common.base.Verify
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.patofch.rickandmorty.domain.model.Character
import com.patofch.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetCharacterByIdTest {

    @Mock
    private lateinit var characterRepository: CharacterRepository

    private lateinit var getCharacterById: GetCharacterById

    private val character = Character(1, "Fake Name", "url")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCharacterById = GetCharacterById(characterRepository)
    }

    @Test
    fun getCharactersById() {
        runBlocking {
            val fakeId = 1
            whenever(characterRepository.getCharacterById(fakeId)).doReturn(character)
            val character = getCharacterById(fakeId)

            Verify.verify(character.id == fakeId)
            verify(characterRepository).getCharacterById(fakeId)
        }
    }
}