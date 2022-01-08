package com.patofch.rickandmorty.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.patofch.rickandmorty.core.Converters
import com.patofch.rickandmorty.feature_character.data.data_source.db.CharacterDao
import com.patofch.rickandmorty.feature_character.data.data_source.db.model.CharacterDtoEntity

@Database(
    entities = [
        CharacterDtoEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RickAndMortyDatabase: RoomDatabase() {

    abstract val characterDao: CharacterDao

    companion object {
        const val DATABASE_NAME = "rickandmorty_db"
    }
}