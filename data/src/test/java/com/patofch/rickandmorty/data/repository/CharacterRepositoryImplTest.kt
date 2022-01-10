package com.patofch.rickandmorty.data.repository

import com.nhaarman.mockitokotlin2.*
import com.patofch.rickandmorty.data.data_source.api.CharacterService
import com.patofch.rickandmorty.data.data_source.api.NetworkController
import com.patofch.rickandmorty.data.data_source.api.model.CharacterApiEntityMapper
import com.patofch.rickandmorty.data.data_source.api.model.CharacterApiResponse
import com.patofch.rickandmorty.data.data_source.db.CharacterDao
import com.patofch.rickandmorty.data.data_source.db.model.CharacterDtoEntity
import com.patofch.rickandmorty.data.utils.getFakeCharacter
import com.patofch.rickandmorty.data.utils.getFakeCharacterApi
import com.patofch.rickandmorty.data.utils.getFakeCharacterDto
import com.patofch.rickandmorty.domain.model.CharacterEntityMapper
import com.patofch.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CharacterRepositoryImplTest {

    @Mock
    private lateinit var characterService: CharacterService

    @Mock
    private lateinit var characterDao: CharacterDao

    @Mock
    private lateinit var dtoEntityMapper: CharacterEntityMapper<CharacterDtoEntity>

    @Mock
    private lateinit var apiEntityMapper: CharacterApiEntityMapper

    @Mock
    private lateinit var networkController: NetworkController

    private lateinit var characterRepository: CharacterRepository

    private val fakeCharacter = getFakeCharacter()

    private val fakeCharacterApi = getFakeCharacterApi()

    private val fakeCharacterDto = getFakeCharacterDto()

    private val response = CharacterApiResponse(
        info = CharacterApiResponse.ResponseInfo(1, "", 1, ""),
        results = listOf(fakeCharacterApi)
    )


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        characterRepository = CharacterRepositoryImpl(
            characterService,
            characterDao,
            dtoEntityMapper,
            apiEntityMapper,
            networkController
        )
    }

    @Test
    fun `verify getCharacters calls characterDao getCharacters`() {
        runBlocking {
            whenever(networkController.isNetworkConnected()).doReturn(false)
            whenever(characterDao.getCharacters()).doReturn(flowOf(listOf(fakeCharacterDto)))

            characterRepository.getCharacters().collect()

            verify(networkController).isNetworkConnected()
            verifyBlocking(dtoEntityMapper) {
                mapToCharacter(fakeCharacterDto)
            }
            verify(characterDao).getCharacters()
        }
    }

    @Test
    fun `verify loadMoreCharacters with disconnected network doesn't call character service`() {
        runBlocking {
            whenever(networkController.isNetworkConnected()).doReturn(false)

            characterRepository.loadMoreCharacters()

            verifyZeroInteractions(characterService)
        }
    }

    @Test
    fun `verify loadMoreCharacters with connected network calls character service`() {
        runBlocking {
            whenever(networkController.isNetworkConnected()).doReturn(true)

            characterRepository.loadMoreCharacters()

            verify(characterService).getCharacters(1)
        }
    }

    @Test
    fun `verify loadMoreCharacters when service success calls characterDao insertCharacters`() {
        runBlocking {
            whenever(networkController.isNetworkConnected()).doReturn(true)
            whenever(characterService.getCharacters(1)).doReturn(response)
            whenever(apiEntityMapper.mapToCharacterDtoEntity(fakeCharacterApi)).doReturn(fakeCharacterDto)

            characterRepository.loadMoreCharacters()

            verifyBlocking(apiEntityMapper) {
                mapToCharacterDtoEntity(fakeCharacterApi)
            }
            verify(characterDao).insertCharacters(listOf(fakeCharacterDto))
        }
    }

    @Test
    fun `verify loadMoreCharacters when service error doesn't call characterDao insertCharacters`() {
        runBlocking {
            whenever(networkController.isNetworkConnected()).doReturn(true)
            given(characterService.getCharacters(1)).willAnswer { throw Exception()}

            characterRepository.loadMoreCharacters()

            verifyZeroInteractions(characterDao)
        }
    }

    @Test
    fun `verify getCharacterById with disconnected network doesn't call character service`() {
        runBlocking {
            whenever(networkController.isNetworkConnected()).doReturn(false)

            characterRepository.getCharacterById(1)

            verifyZeroInteractions(characterService)
        }
    }

    @Test
    fun `verify getCharacterById with connected network calls character service`() {
        runBlocking {
            val characterId = 1
            whenever(networkController.isNetworkConnected()).doReturn(true)
            whenever(characterService.getCharacterById(characterId)).doReturn(fakeCharacterApi)
            whenever(apiEntityMapper.mapToCharacterDtoEntity(fakeCharacterApi)).doReturn(fakeCharacterDto)
            whenever(dtoEntityMapper.mapToCharacter(fakeCharacterDto)).doReturn(fakeCharacter)

            val character = characterRepository.getCharacterById(characterId)

            verifyBlocking(apiEntityMapper) {
                mapToCharacterDtoEntity(fakeCharacterApi)
            }
            verifyBlocking(dtoEntityMapper) {
                mapToCharacter(fakeCharacterDto)
            }
            verify(characterService).getCharacterById(characterId)
            assert(character!! == fakeCharacter)
        }
    }

    @Test
    fun `verify getCharactersFilterBy with disconnected network doesn't call character service`() {
        runBlocking {
            whenever(networkController.isNetworkConnected()).doReturn(false)

            characterRepository.getCharactersFilterBy()

            verifyZeroInteractions(characterService)
            verify(characterDao).getCharacters()
        }
    }

    @Test
    fun `verify getCharactersFilterBy with connected network calls character service`() {
        val fakeName = "fakeName"
        val fakeStatus = "fakeStatus"
        val fakeSpecies = "fakeSpecies"
        val fakeType = "fakeType"
        val fakeGender = "fakeGender"

        runBlocking {
            whenever(networkController.isNetworkConnected()).doReturn(true)
            whenever(characterService.getCharacters(
                name = fakeName,
                status = fakeStatus,
                species = fakeSpecies,
                type = fakeType,
                gender = fakeGender
            )).doReturn(response)
            whenever(apiEntityMapper.mapToCharacterDtoEntity(fakeCharacterApi)).doReturn(fakeCharacterDto)
            whenever(dtoEntityMapper.mapToCharacter(fakeCharacterDto)).doReturn(fakeCharacter)

            characterRepository.getCharactersFilterBy(
                name = fakeName,
                status = fakeStatus,
                species = fakeSpecies,
                type = fakeType,
                gender = fakeGender
            ).collect()

            verifyBlocking(characterService) {
                getCharacters(
                    name = fakeName,
                    status = fakeStatus,
                    species = fakeSpecies,
                    type = fakeType,
                    gender = fakeGender
                )
            }
            verifyBlocking(apiEntityMapper) {
                mapToCharacterDtoEntity(fakeCharacterApi)
            }
            verifyBlocking(dtoEntityMapper) {
                mapToCharacter(fakeCharacterDto)
            }
            verifyZeroInteractions(characterDao)
        }
    }
}