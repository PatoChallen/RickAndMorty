package com.patofch.rickandmorty.feature_character.data.data_source.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.patofch.rickandmorty.feature_character.data.data_source.db.model.CharacterDtoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characterdtoentity")
    fun getCharacters(): Flow<List<CharacterDtoEntity>>

    @Query("SELECT * FROM characterdtoentity WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterDtoEntity?

    @Query("SELECT * FROM characterdtoentity WHERE name LIKE '%' || :name || '%' AND status LIKE '%' || :status || '%' AND species LIKE '%' || :species || '%' AND type LIKE '%' || :type || '%' AND gender LIKE '%' || :gender || '%'")
    fun getCharacterFilterBy(
        name: String? = "",
        status: String? = "",
        species: String? = "",
        type: String? = "",
        gender: String? = ""
    ): Flow<List<CharacterDtoEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertCharacters(characters: List<CharacterDtoEntity>)

    @Delete
    suspend fun deleteCharacter(character: CharacterDtoEntity)
}