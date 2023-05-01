package models

import locale.toLocalDateTime
import locale.toLocalMoney
import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

data class Ingrediente(
    val id: Long, // Los id siempre serán Long
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val price: Double,
    val disponible: Boolean = true,
    val cantidad: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Serializable {
    override fun toString(): String {
        return "Ingrediente(id=$id, uuid=$uuid, name='$name', price=$price, disponible=$disponible, cantidad=$cantidad, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}