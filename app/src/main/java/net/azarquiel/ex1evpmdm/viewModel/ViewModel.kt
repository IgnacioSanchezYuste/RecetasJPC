package net.azarquiel.ex1evpmdm.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.azarquiel.ex1evpmdm.model.RecetaConIngredientes
import net.azarquiel.ex1evpmdm.model.RecetaIngrediente
import net.azarquiel.ex1evpmdm.repository.RecetaRepository

class RecetaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecetaRepository(application)

    val allRecetas: LiveData<List<RecetaConIngredientes>> = repository.getAllRecetas()

    fun getRecetaById(id: Int): LiveData<RecetaConIngredientes> {
        return repository.getRecetaById(id)
    }

    fun getIngredientesConCantidad(recetaId: Int): LiveData<List<RecetaIngrediente>> {
        return repository.getIngredientesConCantidad(recetaId)
    }
}