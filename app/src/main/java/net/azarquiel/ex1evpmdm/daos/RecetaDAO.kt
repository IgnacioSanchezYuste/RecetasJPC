package net.azarquiel.ex1evpmdm.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import net.azarquiel.ex1evpmdm.model.RecetaConIngredientes

@Dao
interface RecetaDAO {
    @Transaction
    @Query("SELECT * FROM receta")
    fun getRecetasConIngredientes(): LiveData<List<RecetaConIngredientes>>

    @Transaction
    @Query("SELECT * FROM receta WHERE meal LIKE '%' || :query || '%'")
    fun searchRecetasByName(query: String): LiveData<List<RecetaConIngredientes>>

    @Transaction
    @Query("SELECT * FROM receta WHERE area_id = :areaId")
    fun getRecetasByArea(areaId: Int): LiveData<List<RecetaConIngredientes>>

    @Transaction
    @Query("SELECT * FROM receta WHERE categoria_id = :categoriaId")
    fun getRecetasByCategoria(categoriaId: Int): LiveData<List<RecetaConIngredientes>>

    @Transaction
    @Query("SELECT * FROM receta WHERE idMeal = :id")
    fun getRecetaById(id: Int): LiveData<RecetaConIngredientes>
}