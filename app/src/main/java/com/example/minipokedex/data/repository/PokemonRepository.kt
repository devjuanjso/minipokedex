package com.example.minipokedex.data.repository

import com.example.minipokedex.data.api.ApiClient
import com.example.minipokedex.data.models.Pokemon
import com.example.minipokedex.data.models.PokemonDetail

class PokemonRepository {
    private val apiService = ApiClient.pokeApiService

    suspend fun getPokemonList(): List<Pokemon> {
        return try {
            apiService.getPokemonList(limit = 100).results
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getPokemonDetail(idOrName: String): PokemonDetail? {
        return try {
            apiService.getPokemonDetail(idOrName)
        } catch (e: Exception) {
            null
        }
    }
}