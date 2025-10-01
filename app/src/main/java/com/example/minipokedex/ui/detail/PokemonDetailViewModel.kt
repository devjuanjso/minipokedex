package com.example.minipokedex.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.minipokedex.data.models.PokemonDetail
import com.example.minipokedex.data.repository.PokemonRepository

class PokemonDetailViewModel : ViewModel() {
    private val repository = PokemonRepository()

    private val _pokemonDetail = MutableStateFlow<PokemonDetail?>(null)
    val pokemonDetail: StateFlow<PokemonDetail?> = _pokemonDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadPokemonDetail(idOrName: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val detail = repository.getPokemonDetail(idOrName)
            _pokemonDetail.value = detail
            _isLoading.value = false
        }
    }
}