package models

import locale.toLocalDate
import java.time.LocalDate
import java.util.*

data class Ingrediente(
    val id: Long = 0,
    val uuid: UUID,
    val nombre: String,
    val precio: Double,
    val cantidad: Int,
    val createAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    val disponible: Boolean = cantidad > 0
){
    override fun toString(): String {
        return "Ingrediente(id=$id, " +
                "uuid=$uuid," +
                "nombre='$nombre', " +
                "precio=$precio, " +
                "cantidad=$cantidad, " +
                "createAt=${createAt.toLocalDate()}, " +
                "updatedAt=${updatedAt.toLocalDate()}, " +
                "disponible=$disponible)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ingrediente

        if (uuid != other.uuid) return false
        if (nombre != other.nombre) return false
        if (precio != other.precio) return false
        if (cantidad != other.cantidad) return false
        if (createAt != other.createAt) return false
        if (updatedAt != other.updatedAt) return false
        if (disponible != other.disponible) return false

        return true
    }
}