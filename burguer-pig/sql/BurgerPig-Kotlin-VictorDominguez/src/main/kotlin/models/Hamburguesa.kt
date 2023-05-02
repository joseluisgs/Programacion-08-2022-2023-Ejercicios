package models

import java.util.*

data class Hamburguesa(
    val id: UUID,
    val nombre: String,
    val ingredientes: MutableList<Ingrediente>,
    var precio: Double = ingredientes.sumOf { it.precio }
) {
    override fun toString(): String {
        return "Hamburguesa -> ID: $id, " +
                "Nombre: $nombre, " +
                "Ingredientes --- $ingredientes ---," +
                "Precio: $precio"
    }
}