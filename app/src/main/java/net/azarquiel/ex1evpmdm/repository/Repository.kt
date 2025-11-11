package net.azarquiel.ex1evpmdm.repository

import android.app.Application
import androidx.lifecycle.LiveData
import net.azarquiel.ex1evpmdm.model.Area
import net.azarquiel.ex1evpmdm.model.Categoria
import net.azarquiel.ex1evpmdm.model.Ingrediente
import net.azarquiel.ex1evpmdm.model.RecetaConIngredientes
import net.azarquiel.ex1evpmdm.model.RecetaIngrediente
import net.azarquiel.ex1evpmdm.model.RecetasDB

class RecetaRepository(application: Application) {

    private val recetaDAO = RecetasDB.getDatabase(application).recetaDAO()
    private val areaDAO = RecetasDB.getDatabase(application).areaDAO()
    private val categoriaDAO = RecetasDB.getDatabase(application).categoriaDAO()
    private val ingredienteDAO = RecetasDB.getDatabase(application).ingredienteDAO()
    private val recetaIngredienteDAO = RecetasDB.getDatabase(application).recetaIngredienteDAO()

    fun getAllRecetas(): LiveData<List<RecetaConIngredientes>> {
        return recetaDAO.getRecetasConIngredientes()
    }

    fun getRecetaById(id: Int): LiveData<RecetaConIngredientes> {
        return recetaDAO.getRecetaById(id)
    }

    fun getAllAreas(): LiveData<List<Area>> {
        return areaDAO.getAllAreas()
    }

    fun getAllCategorias(): LiveData<List<Categoria>> {
        return categoriaDAO.getAllCategorias()
    }

    fun getAllIngredientes(): LiveData<List<Ingrediente>> {
        return ingredienteDAO.getAllIngredientes()
    }

    fun searchRecetasByName(query: String): LiveData<List<RecetaConIngredientes>> {
        return recetaDAO.searchRecetasByName(query)
    }

    fun getRecetasByArea(areaId: Int): LiveData<List<RecetaConIngredientes>> {
        return recetaDAO.getRecetasByArea(areaId)
    }

    fun getRecetasByCategoria(categoriaId: Int): LiveData<List<RecetaConIngredientes>> {
        return recetaDAO.getRecetasByCategoria(categoriaId)
    }

    fun searchAreas(query: String): LiveData<List<Area>> {
        return areaDAO.searchAreas(query)
    }

    fun searchCategorias(query: String): LiveData<List<Categoria>> {
        return categoriaDAO.searchCategorias(query)
    }

    fun filterRecetas(tipoFiltro: TipoFiltro, query: String): LiveData<List<RecetaConIngredientes>> {
        return when(tipoFiltro) {
            TipoFiltro.NOMBRE -> recetaDAO.searchRecetasByName(query)
            TipoFiltro.AREA -> recetaDAO.getRecetasConIngredientes() // Por simplificar ahora
            TipoFiltro.CATEGORIA -> recetaDAO.getRecetasConIngredientes() // Por simplificar ahora
        }
    }

    suspend fun insertIngredienteEnReceta(recetaIngrediente: RecetaIngrediente) {
        recetaIngredienteDAO.insert(recetaIngrediente)
    }

    suspend fun deleteIngredientesDeReceta(recetaId: Int) {
        recetaIngredienteDAO.deleteByRecetaId(recetaId)
    }

    fun getIngredientesConCantidad(recetaId: Int): LiveData<List<RecetaIngrediente>> {
        return recetaIngredienteDAO.getIngredientesConCantidadByRecetaId(recetaId)
    }
}

enum class TipoFiltro {
    NOMBRE, AREA, CATEGORIA
}