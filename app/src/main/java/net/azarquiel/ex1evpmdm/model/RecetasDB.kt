package net.azarquiel.ex1evpmdm.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.azarquiel.ex1evpmdm.daos.AreaDAO
import net.azarquiel.ex1evpmdm.daos.CategoriaDAO
import net.azarquiel.ex1evpmdm.daos.IngredienteDAO
import net.azarquiel.ex1evpmdm.daos.RecetaDAO
import net.azarquiel.ex1evpmdm.daos.RecetaIngredienteDAO

@Database(
    entities = [
        Area::class,
        Categoria::class,
        Ingrediente::class,
        Receta::class,
        RecetaIngrediente::class
    ],
    version = 1
)
abstract class RecetasDB : RoomDatabase() {
    abstract fun recetaDAO(): RecetaDAO
    abstract fun areaDAO(): AreaDAO
    abstract fun categoriaDAO(): CategoriaDAO
    abstract fun ingredienteDAO(): IngredienteDAO
    abstract fun recetaIngredienteDAO(): RecetaIngredienteDAO

    companion object {
        @Volatile
        private var INSTANCE: RecetasDB? = null

        fun getDatabase(context: Context): RecetasDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecetasDB::class.java,
                    "recetas.db"
                ).createFromAsset("recetas.db") // ← SIN la carpeta "databases/"
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}