package com.patofch.rickandmorty.data.data_source.api

import com.patofch.rickandmorty.data.data_source.api.model.CharacterApiEntity
import com.patofch.rickandmorty.data.data_source.api.model.CharacterApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CharacterService {

    @GET("/api/character/")
    suspend fun getCharacters(
        @Query("page") page: Int? = 1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null,
    ): CharacterApiResponse

    @GET("/api/character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterApiEntity
}