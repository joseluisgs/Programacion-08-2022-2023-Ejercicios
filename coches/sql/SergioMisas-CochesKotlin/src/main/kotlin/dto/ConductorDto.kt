package dto

import com.squareup.moshi.Json
import models.Conductor
import java.time.LocalDate
import java.util.*

data class ConductorDto(
    @Json(name = "uuid") val uuid: String,
    @Json(name = "nombre") val nombre: String,
    @Json(name = "fechaCarnet") val fechaCarnet: String,
)

fun Conductor.toDto() = ConductorDto(
    this.uuid.toString(),
    this.nombre,
    this.fechaCarnet.toString()
)

fun ConductorDto.toConductor() = Conductor(
    UUID.fromString(this.uuid),
    this.nombre,
    LocalDate.parse(this.fechaCarnet)
)
