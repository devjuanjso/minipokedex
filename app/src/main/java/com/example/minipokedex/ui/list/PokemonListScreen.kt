package com.example.minipokedex.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.minipokedex.data.models.Pokemon

@Composable
fun PokemonListScreen(
    onPokemonClick: (String, String) -> Unit,
    viewModel: PokemonListViewModel = viewModel()
) {
    val pokemons by viewModel.pokemons.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadPokemons()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.filterPokemons(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Buscar Pokémon...") },
            singleLine = true
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pokemons) { pokemon ->
                    PokemonItem(
                        pokemon = pokemon,
                        onItemClick = {
                            onPokemonClick(pokemon.id.toString(), pokemon.name)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonItem(
    pokemon: Pokemon,
    onItemClick: (Pokemon) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = { onItemClick(pokemon) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "#${pokemon.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}