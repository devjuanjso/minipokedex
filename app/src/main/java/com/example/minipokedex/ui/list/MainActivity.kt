package com.example.minipokedex.ui.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.minipokedex.ui.theme.Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "pokemon_list"
                    ) {
                        composable("pokemon_list") {
                            PokemonListScreen(
                                onPokemonClick = { pokemonId: String, pokemonName: String ->
                                    navController.navigate("pokemon_detail/$pokemonId/$pokemonName")
                                }
                            )
                        }
                        composable("pokemon_detail/{pokemonId}/{pokemonName}") { backStackEntry ->
                            val pokemonId = backStackEntry.arguments?.getString("pokemonId") ?: ""
                            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
                            com.example.minipokedex.ui.detail.PokemonDetailScreen(
                                pokemonId = pokemonId,
                                pokemonName = pokemonName,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}