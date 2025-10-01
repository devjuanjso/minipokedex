package com.example.minipokedex.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.minipokedex.ui.theme.Theme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val pokemonId = intent.getStringExtra("POKEMON_ID") ?: ""
                    val pokemonName = intent.getStringExtra("POKEMON_NAME") ?: ""

                    PokemonDetailScreen(
                        pokemonId = pokemonId,
                        pokemonName = pokemonName,
                        onBackClick = { finish() }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    pokemonId: String,
    pokemonName: String,
    onBackClick: () -> Unit,
    viewModel: PokemonDetailViewModel = viewModel()
) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetail(pokemonId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        pokemonDetail?.name?.replaceFirstChar { it.uppercase() }
                            ?: pokemonName.replaceFirstChar { it.uppercase() }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (pokemonDetail != null) {
            PokemonDetailContent(
                pokemonDetail = pokemonDetail!!,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun PokemonDetailContent(
    pokemonDetail: com.example.minipokedex.data.models.PokemonDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Imagem do Pokémon
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = pokemonDetail.sprites.other?.officialArtwork?.frontDefault
                    ?: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonDetail.id}.png",
                contentDescription = pokemonDetail.name,
                modifier = Modifier.size(180.dp)
            )
        }

        // Informações básicas
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "#${pokemonDetail.id} ${pokemonDetail.name.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tipos
            Text(
                text = "Tipos:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = pokemonDetail.types.joinToString { it.type.name.replaceFirstChar { char -> char.uppercase() } },
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Altura e Peso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Altura",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${pokemonDetail.heightInMeters} m",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column {
                    Text(
                        text = "Peso",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${pokemonDetail.weightInKg} kg",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estatísticas
            Text(
                text = "Estatísticas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            pokemonDetail.stats.forEach { stat ->
                StatItem(
                    statName = when (stat.stat.name) {
                        "hp" -> "HP"
                        "attack" -> "Ataque"
                        "defense" -> "Defesa"
                        "special-attack" -> "Ataque Especial"
                        "special-defense" -> "Defesa Especial"
                        "speed" -> "Velocidade"
                        else -> stat.stat.name.replaceFirstChar { it.uppercase() }
                    },
                    value = stat.baseStat
                )
            }
        }
    }
}

@Composable
fun StatItem(statName: String, value: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = statName,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}