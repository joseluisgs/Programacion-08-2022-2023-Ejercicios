package mappers

import dto.ModuloDto
import models.Grado
import models.Modulo
import java.util.*

fun Modulo.toDto(): ModuloDto{
    return ModuloDto(
        uuid.toString(),
        nombre,
        curso.toString(),
        grado.toString()
    )
}

fun ModuloDto.toClass(): Modulo{
    return Modulo(
        UUID.fromString(uuid),
        nombre,
        curso.toInt(),
        Grado.valueOf(grado)
    )
}