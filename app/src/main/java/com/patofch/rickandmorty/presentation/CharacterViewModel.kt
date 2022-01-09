package com.patofch.rickandmorty.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patofch.rickandmorty.domain.model.Character
import com.patofch.rickandmorty.domain.use_case.CharacterUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterUseCases: CharacterUseCases,
    @Named("NetworkState") private val networkConnected: Boolean
) : ViewModel() {

    private val _characters: MutableState<List<Character>> = mutableStateOf(listOf())
    val characters: State<List<Character>> get() = _characters

    private var job: Job? = null
    init {
        Log.e("NetworkState", "network connected: $networkConnected")
        getCharacters()
    }

    fun getMoreCharacters() {
        viewModelScope.launch {
            characterUseCases.loadMoreCharacters()
        }
    }

    fun getCharacters() {
        job?.cancel()
        job = viewModelScope.launch {
            characterUseCases.getCharacters().collect {
                Log.e("TAG", "getCharacters collect ${it.size}")
                _characters.value = it
            }
        }
    }

    fun getCharactersFilterBy(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ) {
        job?.cancel()
        job = viewModelScope.launch {
            characterUseCases.getCharactersFilterBy(
                name,
                status,
                species,
                type,
                gender
            ).collect {
                Log.e("TAG", "getCharactersFilterBy collect ${it.size}")
                _characters.value = it
            }
        }
    }
}