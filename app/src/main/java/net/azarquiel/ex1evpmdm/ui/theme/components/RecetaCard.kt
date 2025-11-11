package net.azarquiel.ex1evpmdm.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import net.azarquiel.ex1evpmdm.model.RecetaConIngredientes

@Composable
fun RecetaCard(
    receta: RecetaConIngredientes,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apirecetas/img/${receta.recetaCompleta.receta.image}",
                contentDescription = "Imagen de ${receta.recetaCompleta.receta.meal}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = receta.recetaCompleta.receta.meal,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Área: ${receta.recetaCompleta.area.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Categoría: ${receta.recetaCompleta.categoria.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}