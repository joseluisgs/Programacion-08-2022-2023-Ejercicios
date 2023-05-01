package dto

import com.squareup.moshi.Json
import models.Coche

data class CochesDto(
    @Json(name = "coches") val coches: List<CocheDto>
)

fun List<Coche>.toDto() = CochesDto(map { it.toDto() })
