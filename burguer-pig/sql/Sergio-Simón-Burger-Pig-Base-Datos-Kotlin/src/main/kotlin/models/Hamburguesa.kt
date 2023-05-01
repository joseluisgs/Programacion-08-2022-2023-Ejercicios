package models

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class Hamburguesa(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val cantidad: Int = 1,
    val lineaHamburguesa: MutableList<LineaHamburguesa> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
): Serializable {
  val precio: Double
    get() = lineaHamburguesa.sumOf { it.precioTotal }

    val totalIngrediente: Int
        get() = lineaHamburguesa.sumOf { it.cantidadIngrediente}

    val nextLineaId: Long
        get() = lineaHamburguesa.size + 1L

    fun addLineaIngrediente(linea: LineaHamburguesa){
        lineaHamburguesa.add(linea)
    }

    fun removeLineaIngredient(lineaId: Long){
        lineaHamburguesa.removeIf { it.lineaId == lineaId }
    }

    override fun toString(): String {
        return "Hamburguesa(id=$id, uuid=$uuid, name='$name', cantidad=$cantidad, lineaHamburguesa=$lineaHamburguesa, createdAt=$createdAt, updatedAt=$updatedAt, precio=$precio, totalIngrediente=$totalIngrediente)"
    }
}