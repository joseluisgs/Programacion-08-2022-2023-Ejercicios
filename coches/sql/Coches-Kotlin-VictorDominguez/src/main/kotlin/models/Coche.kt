package models

import enums.TipoMotor
import java.util.UUID

data class Coche(
    val id: Long = 0L,
    val idCond: UUID,
    val marca: String,
    val modelo: String,
    val precio: Double,
    val tipoMotor: TipoMotor
) {
    override fun toString(): String {
        return "Coche -> ID: $id, " +
                "ID Conductor: $idCond, " +
                "Marca: $marca, " +
                "Modelo: $modelo, " +
                "Precio: $precio, " +
                "Tipo Motor: $tipoMotor"
    }
}