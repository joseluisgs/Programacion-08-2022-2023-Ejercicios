package dto

import com.squareup.moshi.Json
import models.Coche


data class CocheDto(
    @Json(name = "id") val id: Long,
    @Json(name = "marca") val marca: String,
    @Json(name = "modelo") val modelo: String,
    @Json(name = "precio") val precio: Double,
    @Json(name = "motor") val motor: String,
    @Json(name = "conductor") val conductor: ConductorDto

)

fun Coche.toDto() = CocheDto(
    this.id,
    this.marca,
    this.modelo,
    this.precio,
    this.motor.toString(),
    this.conductor.toDto()
)
