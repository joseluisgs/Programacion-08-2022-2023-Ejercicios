package models

import java.time.LocalDate
import java.util.UUID

data class Conductor(
    val uuid: UUID,
    val nombre: String,
    val fechaCarnet: LocalDate,
    val coches: List<Coche>
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Conductor) return false
        return uuid == other.uuid &&
                nombre == other.nombre &&
                fechaCarnet == other.fechaCarnet
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + nombre.hashCode()
        result = 31 * result + fechaCarnet.hashCode()
        return result
    }
}