package net.azarquiel.ex1evpmdm.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import net.azarquiel.ex1evpmdm.model.Area


@Dao
interface AreaDAO {
    @Query("SELECT * FROM area")
    fun getAllAreas(): LiveData<List<Area>>

    @Query("SELECT * FROM area WHERE id = :id")
    fun getAreaById(id: Int): LiveData<Area>

    @Query("SELECT * FROM area WHERE name LIKE '%' || :query || '%'")
    fun searchAreas(query: String): LiveData<List<Area>>
}
