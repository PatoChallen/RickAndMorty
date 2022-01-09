package com.patofch.rickandmorty.data.repository

import android.util.Log
import com.patofch.rickandmorty.data.data_source.api.CharacterService
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
import javax.inject.Named

internal class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao,
    private val dtoEntityMapper: CharacterEntityMapper<CharacterDtoEntity>,
    private val apiEntityMapper: CharacterApiEntityMapper,
    @Named("NetworkState") private val networkConnected: Boolean
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

    override suspend fun getCharacterById(id: Int): Character {
        return dtoEntityMapper.mapToCharacter(
            apiEntityMapper.mapToCharacterDtoEntity(
                characterService.getCharacterById(id)
            )
        )
    }

    override suspend fun loadMoreCharacters() {
        if (networkConnected) {
            val time = System.currentTimeMillis()
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
                val newTime = System.currentTimeMillis()
                Log.e("TAG", "loadMoreCharacters: ${ (time - newTime) / 1000 }")
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
        return if (networkConnected) {
            flow {
                val time = System.currentTimeMillis()
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
                    val newTime = System.currentTimeMillis()
                    Log.e("TAG", "getCharactersFilterBy: ${ (time - newTime) / 1000 }")
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