package com.example.minipokedex.data.api

import com.example.minipokedex.data.models.PokemonDetail
import com.example.minipokedex.data.models.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{idOrName}")
    suspend fun getPokemonDetail(
        @Path("idOrName") idOrName: String
    ): PokemonDetail
}