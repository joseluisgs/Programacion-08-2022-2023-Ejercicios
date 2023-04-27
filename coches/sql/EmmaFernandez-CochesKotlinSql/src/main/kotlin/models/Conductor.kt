package models

import locale.toLocalDate
import java.time.LocalDate
import java.util.UUID

data class Conductor(
    val uuid: UUID,
    val nombre: String,
    val fechaCarnet: LocalDate
) {
    fun toLocalString(): String =
        "Conductor(uuid=$uuid, nombre=$nombre, fechaCarnet=${fechaCarnet.toLocalDate()})"
}
