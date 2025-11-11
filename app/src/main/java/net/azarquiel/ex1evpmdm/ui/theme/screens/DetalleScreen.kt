package net.azarquiel.ex1evpmdm.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import coil.compose.AsyncImage
import net.azarquiel.ex1evpmdm.viewModel.RecetaViewModel

@Composable
fun DetalleScreen(
    recetaId: Int,
    viewModel: RecetaViewModel
) {
    val receta by viewModel.getRecetaById(recetaId).observeAsState()
    val ingredientesConCantidad by viewModel.getIngredientesConCantidad(recetaId).observeAsState(emptyList())

    var ingredientesMostrar by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    LaunchedEffect(receta, ingredientesConCantidad) {
        if (receta != null && ingredientesConCantidad.isNotEmpty()) {
            val combinados = mutableListOf<Pair<String, String>>()

            ingredientesConCantidad.forEach { recetaIngrediente ->
                val ingrediente = receta!!.ingredientes.find { it.id == recetaIngrediente.ingrediente_id }
                if (ingrediente != null) {
                    combinados.add(Pair(ingrediente.name, recetaIngrediente.cantidad))
                }
            }

            ingredientesMostrar = combinados
        } else if (receta != null) {
            ingredientesMostrar = receta!!.ingredientes.map { Pair(it.name, "") }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (receta != null) {
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apirecetas/img/${receta!!.recetaCompleta.receta.image}",
                contentDescription = "Imagen de ${receta!!.recetaCompleta.receta.meal}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = receta!!.recetaCompleta.receta.meal,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(modifier = Modifier.padding(bottom = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Área: ${receta!!.recetaCompleta.area.name}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Categoría: ${receta!!.recetaCompleta.categoria.name}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Card(modifier = Modifier.padding(bottom = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Ingredientes:",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ingredientesMostrar.forEach { (nombre, cantidad) ->
                        Text(
                            text = if (cantidad.isNotEmpty()) {
                                "- $nombre ($cantidad)"
                            } else {
                                "- $nombre"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }

            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Instrucciones:",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = receta!!.recetaCompleta.receta.instructions,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            Text("Cargando receta...")
        }
    }
}