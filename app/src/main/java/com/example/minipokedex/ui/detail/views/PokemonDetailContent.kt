package com.example.minipokedex.ui.detail.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.minipokedex.data.models.PokemonDetail

@Composable
fun PokemonDetailContent(
    pokemonDetail: PokemonDetail,
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