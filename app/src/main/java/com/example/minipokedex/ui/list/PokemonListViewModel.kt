package com.example.minipokedex.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.minipokedex.data.models.Pokemon
import com.example.minipokedex.data.repository.PokemonRepository

class PokemonListViewModel : ViewModel() {
    private val repository = PokemonRepository()

    private val _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> = _pokemons.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var originalPokemons = listOf<Pokemon>()

    fun loadPokemons() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getPokemonList()
            originalPokemons = result
            _pokemons.value = result
            _isLoading.value = false
        }
    }

    fun filterPokemons(query: String) {
        if (query.isEmpty()) {
            _pokemons.value = originalPokemons
        } else {
            _pokemons.value = originalPokemons.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}