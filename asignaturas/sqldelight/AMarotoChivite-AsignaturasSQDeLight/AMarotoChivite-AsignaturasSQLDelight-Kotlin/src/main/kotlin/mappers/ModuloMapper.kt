package mappers

import database.ModuloTable
import models.Modulo
import utils.typeGrade
import java.time.LocalDateTime
import java.util.*

fun toModulo(modulo: ModuloTable): Modulo {

    return Modulo(
        UUID.fromString(modulo.uuid),
        modulo.name,
        modulo.curso.toInt(),
       typeGrade(modulo.grado)
    )
}