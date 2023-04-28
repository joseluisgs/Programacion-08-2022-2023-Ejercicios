package models

import java.io.Serializable

data class LineaHamburguesa(
    val lineaId: Long,
    val idIngrediente: Long,
    val idHamburguesa: Long,
    val precioIngrediente: Double,
    val cantidadIngrediente: Int
): Serializable{
    val precioTotal: Double
        get() = precioIngrediente * cantidadIngrediente

    override fun toString(): String {
        return "LineaHamburguesa(lineaId=$lineaId, idIngrediente=$idIngrediente, idHamburguesa=$idHamburguesa, precioIngrediente=$precioIngrediente, cantidadIngrediente=$cantidadIngrediente, precioTotal=$precioTotal)"
    }
}