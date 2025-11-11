package net.azarquiel.ex1evpmdm.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "area")
data class Area(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String
)

@Entity(tableName = "categoria")
data class Categoria(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String
)

@Entity(tableName = "ingrediente")
data class Ingrediente(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String
)

@Entity(
    tableName = "receta",
    foreignKeys = [
        ForeignKey(
            entity = Area::class,
            parentColumns = ["id"],
            childColumns = ["area_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id"],
            childColumns = ["categoria_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ]
)
data class Receta(
    @PrimaryKey
    @ColumnInfo(name = "idMeal")
    val idMeal: Int,
    @ColumnInfo(name = "meal")
    val meal: String,
    @ColumnInfo(name = "instructions")
    val instructions: String,
    @ColumnInfo(name = "categoria_id")
    val categoria_id: Int,
    @ColumnInfo(name = "area_id")
    val area_id: Int,
    @ColumnInfo(name = "image")
    val image: String
)

@Entity(
    tableName = "receta_ingrediente",
    primaryKeys = ["receta_id", "ingrediente_id"],
    foreignKeys = [
        ForeignKey(
            entity = Receta::class,
            parentColumns = ["idMeal"],
            childColumns = ["receta_id"]
        ),
        ForeignKey(
            entity = Ingrediente::class,
            parentColumns = ["id"],
            childColumns = ["ingrediente_id"]
        )
    ],
    indices = [
        Index(value = ["receta_id"]),
        Index(value = ["ingrediente_id"])  // ← AÑADIR ESTE ÍNDICE
    ]
)
data class RecetaIngrediente(
    @ColumnInfo(name = "receta_id")
    val receta_id: Int,
    @ColumnInfo(name = "ingrediente_id")
    val ingrediente_id: Int,
    @ColumnInfo(name = "cantidad")
    val cantidad: String

)

data class RecetaCompleta(
    @Embedded
    val receta: Receta,
    @Relation(
        parentColumn = "area_id",
        entityColumn = "id"
    )
    val area: Area,
    @Relation(
        parentColumn = "categoria_id",
        entityColumn = "id"
    )
    val categoria: Categoria
)

data class RecetaConIngredientes(
    @Embedded
    val recetaCompleta: RecetaCompleta,

    @Relation(
        entity = Ingrediente::class,
        parentColumn = "idMeal",
        entityColumn = "id",
        associateBy = Junction(
            value = RecetaIngrediente::class,
            parentColumn = "receta_id",
            entityColumn = "ingrediente_id"
        )
    )
    val ingredientes: List<Ingrediente>
)
data class IngredienteConCantidad(
    @Embedded
    val ingrediente: Ingrediente,
    @ColumnInfo(name = "cantidad")
    val cantidad: String
)

