package com.patofch.rickandmorty.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.patofch.rickandmorty.domain.model.Character
import com.patofch.rickandmorty.domain.use_case.CharacterUseCases
import com.patofch.rickandmorty.domain.use_case.GetCharacters
import com.patofch.rickandmorty.domain.use_case.GetCharactersFilterBy
import com.patofch.rickandmorty.domain.use_case.LoadMoreCharacters
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class CharacterViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var characterUseCases: CharacterUseCases

    @Mock
    private lateinit var getCharacters: GetCharacters

    @Mock
    private lateinit var getCharactersFilterBy: GetCharactersFilterBy

    @Mock
    private lateinit var loadMoreCharacters: LoadMoreCharacters

    private lateinit var characterViewModel: CharacterViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val fakeCharacter = Character(1, "fake name", "fake image")

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        characterViewModel = CharacterViewModel(characterUseCases)
    }

    @Test
    fun `verify getMoreCharacters calls characterUseCases loadMoreCharacters`() {
        runBlocking {
            whenever(characterUseCases.loadMoreCharacters).doReturn(loadMoreCharacters)
            whenever(characterUseCases.loadMoreCharacters.invoke()).doReturn(Unit)

            characterViewModel.getMoreCharacters()

            verify(loadMoreCharacters).invoke()
        }
    }

    @Test
    fun `verify getCharacters calls characterUseCases getCharacters`() {
        runBlocking {
            val characterList = listOf(fakeCharacter)
            val characters = characterViewModel.characters
            whenever(characterUseCases.getCharacters).doReturn(getCharacters)
            whenever(characterUseCases.getCharacters()).doReturn(flowOf(characterList))

            characterViewModel.getCharacters()

            assert(characters.value.first() == fakeCharacter)
            verify(getCharacters).invoke()
        }
    }

    @Test
    fun `verify getCharactersFilterBy calls characterUseCases getCharactersFilterBy`() {
        val fakeName = "fakeName"
        val fakeStatus = "fakeStatus"
        val fakeSpecies = "fakeSpecies"
        val fakeType = "fakeType"
        val fakeGender = "fakeGender"

        val characterList = listOf(fakeCharacter)

        runBlocking {
            whenever(characterUseCases.getCharactersFilterBy).doReturn(getCharactersFilterBy)
            whenever(characterUseCases.getCharactersFilterBy(
                name = fakeName,
                status = fakeStatus,
                species = fakeSpecies,
                type = fakeType,
                gender = fakeGender
            )).doReturn(flowOf(characterList))
            val characters = characterViewModel.characters

            characterViewModel.getCharactersFilterBy(
                name = fakeName,
                status = fakeStatus,
                species = fakeSpecies,
                type = fakeType,
                gender = fakeGender
            )

            assert(characters.value.first() == fakeCharacter)
            verify(getCharactersFilterBy).invoke(
                name = fakeName,
                status = fakeStatus,
                species = fakeSpecies,
                type = fakeType,
                gender = fakeGender
            )
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}