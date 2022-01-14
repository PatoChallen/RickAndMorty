package com.patofch.rickandmorty.data.data_source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.patofch.rickandmorty.data.data_source.db.model.CharacterDtoEntity

@Database(
    entities = [
        CharacterDtoEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
internal abstract class RickAndMortyDatabase: RoomDatabase() {

    abstract val characterDao: CharacterDao

    companion object {
        const val DATABASE_NAME = "rickandmorty_db"
    }
}