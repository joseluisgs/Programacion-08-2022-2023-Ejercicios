package Models

import java.util.*

data class Ingredientes(
    val id: Int,
    val uuid: String,
    val nombre: String,
    val precio: Double,
    val cantidad: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val disponible: Boolean
)
