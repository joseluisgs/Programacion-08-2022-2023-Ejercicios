package mappers

import dto.ModulosDto
import dto.ProfesorDto
import models.Profesor
import java.time.LocalDate

fun Profesor.toDto(): ProfesorDto{
    return ProfesorDto(
        id.toString(),
        nombre,
        fechaIncorporacion.toString(),
        ModulosDto(modulos.map { it.toDto() })
    )
}

fun ProfesorDto.toClass(): Profesor{
    return Profesor(
        id.toLong(),
        nombre,
        LocalDate.parse(fechaIncorporacion),
        modulos.modulos.map { it.toClass() }
    )
}