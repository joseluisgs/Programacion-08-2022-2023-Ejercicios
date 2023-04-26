package models

import java.util.*

// Se guarda en otra tabla, de la misma base de datos de burguer
data class LineaBurguer (
    // El id es auto-numerico en la base de datos, es indiferente el valor que introduzcamos al crear el objeto
    val lineaId: Long,
    val burguerUUID: UUID,
    val ingredienteId: Long,
    val ingredienteQuantity: Int,
    val ingredientePrice: Double
) {
    override fun toString(): String {
        return "LineaBurguer(lineaId=$lineaId, burguerUUID=$burguerUUID, ingredienteId=$ingredienteId, ingredienteQuantity=$ingredienteQuantity, ingredientePrice=$ingredientePrice)"
    }
}