package models

import java.util.*

data class Profesor(
    val id: Long = 0,
    val uuid: UUID,
    val nombre: String,
    val experiencia: Int
){
    override fun toString(): String {
        return "Profesor(id=$id, " +
                "uuid=$uuid," +
                "nombre='$nombre', " +
                "experiencia=$experiencia)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Profesor

        if (uuid != other.uuid) return false
        if (nombre != other.nombre) return false
        if (experiencia != other.experiencia) return false

        return true
    }
}