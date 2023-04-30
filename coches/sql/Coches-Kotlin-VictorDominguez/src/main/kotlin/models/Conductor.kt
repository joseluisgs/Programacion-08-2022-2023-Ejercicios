package models

import java.time.LocalDate
import java.util.UUID

data class Conductor(
    val id: UUID,
    val nombre: String,
    val fechaCarnet: LocalDate,
    val coches: MutableList<Coche>
) {
    override fun toString(): String {
        return "Conductor -> ID: $id, " +
                "Nombre: $nombre, " +
                "Fecha Carnet: $fechaCarnet"
    }
}