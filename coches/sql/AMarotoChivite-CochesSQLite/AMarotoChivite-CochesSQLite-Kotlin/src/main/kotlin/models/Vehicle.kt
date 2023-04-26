package models

import java.util.*

data class Vehicle(
    val uuid: UUID,
    val model: String,
    val motor: TypeMotor,
    val foreignUuidConductor: UUID
) {
    enum class TypeMotor {
        GASOLINA,
        DIESEL,
        HIBRIDO,
        ELECTRICO,
        NO_ASIGNED,
        ERROR
    }
}