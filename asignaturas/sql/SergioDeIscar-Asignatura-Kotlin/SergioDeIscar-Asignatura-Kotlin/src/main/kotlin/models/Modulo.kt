package models

import java.util.UUID

data class Modulo(
    val uuid: UUID = UUID.randomUUID(),
    val nombre: String,
    val curso: Int,
    val grado: Grado
)