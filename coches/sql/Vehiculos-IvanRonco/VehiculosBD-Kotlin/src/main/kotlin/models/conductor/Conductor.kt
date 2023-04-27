package models.conductor

import models.vehiculo.Vehiculo
import java.util.UUID

data class Conductor(
    val uuid: UUID,
    val dni: String,
    val nombre: String,
    val apellidos: String,
    val edad: Int,
    val vehiculos: List<Vehiculo> = emptyList()
)