package net.azarquiel.ex1evpmdm.daos

import net.azarquiel.ex1evpmdm.model.Categoria
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface CategoriaDAO {
    @Query("SELECT * FROM categoria")
    fun getAllCategorias(): LiveData<List<Categoria>>

    @Query("SELECT * FROM categoria WHERE id = :id")
    fun getCategoriaById(id: Int): LiveData<Categoria>

    @Query("SELECT * FROM categoria WHERE name LIKE '%' || :query || '%'")
    fun searchCategorias(query: String): LiveData<List<Categoria>>
}
