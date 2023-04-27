package dto

import com.squareup.moshi.Json
import models.Coche
import models.Conductor

data class CocheDto(
    @Json(name = "id") val id: Long,
    @Json(name = "marca") val marca: String,
    @Json(name = "modelo") val modelo: String,
    @Json(name = "precio") val precio: Double,
    @Json(name = "tipoMotor") val tipoMotor: String,
    @Json(name = "conductor") val conductor: ConductorDto
)

fun Coche.toDto() =
    CocheDto(id, marca, modelo, precio, tipoMotor.name, conductor.toDto())
