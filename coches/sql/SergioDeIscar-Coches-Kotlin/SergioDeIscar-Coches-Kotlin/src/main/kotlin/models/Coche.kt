package models

import java.util.UUID

data class Coche(
    var id: Long = -1,
    val marca: String,
    val modelo: String,
    val precio: Float,
    val motor: TypeMotor,
    val idConductor: UUID
)