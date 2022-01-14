package com.patofch.rickandmorty.data.data_source.db

import androidx.room.TypeConverter
import com.google.gson.Gson

internal class Converters {

    @TypeConverter
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<String> = Gson().fromJson(value, Array<String>::class.java).toList()
}