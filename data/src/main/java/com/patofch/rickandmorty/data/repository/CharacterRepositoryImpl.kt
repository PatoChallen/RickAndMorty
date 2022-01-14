package com.patofch.rickandmorty.data.repository

import com.patofch.rickandmorty.data.data_source.api.CharacterService
import com.patofch.rickandmorty.data.data_source.api.NetworkController
import com.patofch.rickandmorty.data.data_source.api.model.CharacterApiEntityMapper
import com.patofch.rickandmorty.data.data_source.db.CharacterDao
import com.patofch.rickandmorty.data.data_source.db.model.CharacterDtoEntity
import com.patofch.rickandmorty.domain.model.Character
import com.patofch.rickandmorty.domain.model.CharacterEntityMapper
import com.patofch.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao,
    private val dtoEntityMapper: CharacterEntityMapper<CharacterDtoEntity>,
    private val apiEntityMapper: CharacterApiEntityMapper,
    private val networkController: NetworkController
) : CharacterRepository {

    private var pageCount = 1
    private var loadMore = true

    override suspend fun getCharacters(): Flow<List<Character>> {
        pageCount = 1
        loadMoreCharacters()
        return characterDao.getCharacters().map { charactersDto ->
            charactersDto.map {
                dtoEntityMapper.mapToCharacter(it)
            }
        }
    }

    override suspend fun getCharacterById(id: Int): Character? {
        if (networkController.isNetworkConnected()) {
            return dtoEntityMapper.mapToCharacter(
                apiEntityMapper.mapToCharacterDtoEntity(
                    characterService.getCharacterById(id)
                )
            )
        }
        return null
    }

    override suspend fun loadMoreCharacters() {
        if (networkController.isNetworkConnected()) {
            try {
                characterDao.insertCharacters(
                    characterService.getCharacters(pageCount).apply {
                        val nextPage = info.next?.substringAfter("?page=")?.substringBefore("&")
                        if (nextPage.isNullOrEmpty()) {
                            loadMore = false
                        } else {
                            pageCount = nextPage.toInt()
                        }
                    }.results.map {
                        apiEntityMapper.mapToCharacterDtoEntity(it)
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun getCharactersFilterBy(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): Flow<List<Character>> {
        return if (networkController.isNetworkConnected()) {
            flow {
                try {
                    emit(
                        characterService.getCharacters(
                            name = name,
                            status = status,
                            species = species,
                            type = type,
                            gender = gender
                        ).results.map {
                            dtoEntityMapper.mapToCharacter(
                                apiEntityMapper.mapToCharacterDtoEntity(it)
                            )
                        }
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            getCharacters().map { characters ->
                characters.filter { character ->
                    name?.let { character.name.contains(name) }
                        ?: status?.let { character.name.contains(status) }
                        ?: species?.let { character.name.contains(species) }
                        ?: type?.let { character.name.contains(type) }
                        ?: gender?.let { character.name.contains(gender) } ?: true
                }
            }
        }
    }
}