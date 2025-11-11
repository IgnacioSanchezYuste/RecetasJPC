package net.azarquiel.ex1evpmdm.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import net.azarquiel.ex1evpmdm.model.Area
import net.azarquiel.ex1evpmdm.model.Ingrediente

@Dao
interface IngredienteDAO {
    @Query("SELECT * FROM ingrediente")
    fun getAllIngredientes(): LiveData<List<Ingrediente>>

    @Query("SELECT * FROM ingrediente WHERE id = :id")
    fun getIngredienteById(id: Int): LiveData<Ingrediente>

    @Query("SELECT * FROM ingrediente WHERE name LIKE '%' || :query || '%'")
    fun searchIngredientes(query: String): LiveData<List<Ingrediente>>
}