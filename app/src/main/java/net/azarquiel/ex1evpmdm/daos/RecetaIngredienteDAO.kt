package net.azarquiel.ex1evpmdm.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import net.azarquiel.ex1evpmdm.model.RecetaIngrediente

@Dao
interface RecetaIngredienteDAO {
    @Insert
    suspend fun insert(recetaIngrediente: RecetaIngrediente)

    @Query("DELETE FROM receta_ingrediente WHERE receta_id = :recetaId")
    suspend fun deleteByRecetaId(recetaId: Int)

    @Query("SELECT * FROM receta_ingrediente WHERE receta_id = :recetaId")
    fun getIngredientesByRecetaId(recetaId: Int): LiveData<List<RecetaIngrediente>>

    // AÑADE ESTE MÉTODO NUEVO
    @Query("SELECT * FROM receta_ingrediente WHERE receta_id = :recetaId")
    fun getIngredientesConCantidadByRecetaId(recetaId: Int): LiveData<List<RecetaIngrediente>>
}