package com.example.minipokedex.ui.list.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.minipokedex.data.models.Pokemon
import androidx.compose.material3.ExperimentalMaterial3Api

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
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrl),
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
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}