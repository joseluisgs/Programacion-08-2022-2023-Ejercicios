package mappers

import dto.CochesDto
import dto.ConductorDto
import models.Conductor
import java.time.LocalDate
import java.util.UUID

fun Conductor.toDto(): ConductorDto{
    return ConductorDto(
        uuid.toString(),
        nombre,
        fechaCarnet.toString(),
        if (coches.isNotEmpty()) CochesDto(coches.map { it.toDto() }) else null
    )
}

fun ConductorDto.toClass(): Conductor{
    return Conductor(
        UUID.fromString(uuid),
        nombre,
        LocalDate.parse(fechaCarnet),
        coches?.let { it.coches.map { it.toClass() } } ?: emptyList()
    )
}