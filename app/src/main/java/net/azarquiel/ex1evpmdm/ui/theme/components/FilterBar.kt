package net.azarquiel.ex1evpmdm.ui.theme.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.azarquiel.ex1evpmdm.repository.TipoFiltro

@Composable
fun FilterBar(
    textoBusqueda: String,
    tipoFiltro: TipoFiltro,
    onTextoCambiado: (String) -> Unit,
    onTipoFiltroCambiado: (TipoFiltro) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = onTextoCambiado,
            placeholder = { Text(getPlaceholderText(tipoFiltro)) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            leadingIcon = {
                Icon(
                    imageVector = getIconForFilter(tipoFiltro),
                    contentDescription = "Tipo de filtro"
                )
            }
        )

        IconButton(onClick = {
            onTipoFiltroCambiado(getNextFilterType(tipoFiltro))
        }) {
            Icon(
                imageVector = getIconForFilter(getNextFilterType(tipoFiltro)),
                contentDescription = "Cambiar tipo de filtro"
            )
        }
    }
}

private fun getIconForFilter(tipoFiltro: TipoFiltro) = when(tipoFiltro) {
    TipoFiltro.NOMBRE -> Icons.Default.Create
    TipoFiltro.AREA -> Icons.Default.Place
    TipoFiltro.CATEGORIA -> Icons.Default.Search
}

private fun getPlaceholderText(tipoFiltro: TipoFiltro) = when(tipoFiltro) {
    TipoFiltro.NOMBRE -> "Buscar por nombre..."
    TipoFiltro.AREA -> "Buscar por área..."
    TipoFiltro.CATEGORIA -> "Buscar por categoría..."
}

private fun getNextFilterType(current: TipoFiltro) = when(current) {
    TipoFiltro.NOMBRE -> TipoFiltro.AREA
    TipoFiltro.AREA -> TipoFiltro.CATEGORIA
    TipoFiltro.CATEGORIA -> TipoFiltro.NOMBRE
}