package net.azarquiel.ex1evpmdm.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import net.azarquiel.ex1evpmdm.repository.TipoFiltro
import net.azarquiel.ex1evpmdm.ui.theme.components.FilterBar
import net.azarquiel.ex1evpmdm.ui.theme.components.RecetaCard
import net.azarquiel.ex1evpmdm.viewModel.RecetaViewModel

@Composable
fun MainScreen(
    onRecetaClick: (Int) -> Unit,
    viewModel: RecetaViewModel
) {
    var textoBusqueda by remember { mutableStateOf("") }
    var tipoFiltro by remember { mutableStateOf(TipoFiltro.NOMBRE) }

    val recetas by viewModel.allRecetas.observeAsState(emptyList())

    val recetasFiltradas = when {
        textoBusqueda.isEmpty() -> recetas
        tipoFiltro == TipoFiltro.NOMBRE -> recetas.filter {
            it.recetaCompleta.receta.meal.contains(textoBusqueda, ignoreCase = true)
        }
        tipoFiltro == TipoFiltro.AREA -> recetas.filter {
            it.recetaCompleta.area.name.contains(textoBusqueda, ignoreCase = true)
        }
        tipoFiltro == TipoFiltro.CATEGORIA -> recetas.filter {
            it.recetaCompleta.categoria.name.contains(textoBusqueda, ignoreCase = true)
        }
        else -> recetas
    }

    Column(modifier = Modifier.fillMaxSize()) {
        FilterBar(
            textoBusqueda = textoBusqueda,
            tipoFiltro = tipoFiltro,
            onTextoCambiado = { textoBusqueda = it },
            onTipoFiltroCambiado = { tipoFiltro = it }
        )

        if (recetasFiltradas.isEmpty()) {
            Text(
                text = if (textoBusqueda.isEmpty()) "Cargando recetas..." else "No se encontraron recetas",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(recetasFiltradas) { receta ->
                    RecetaCard(
                        receta = receta,
                        onClick = { onRecetaClick(receta.recetaCompleta.receta.idMeal) }
                    )
                }
            }
        }
    }
}