package mappers

import database.DocenciaTable
import models.Docencia
import utils.typeGrade
import java.util.*

fun toDocencia(docencia: DocenciaTable): Docencia {

    return Docencia(
        docencia.idProfesor,
        UUID.fromString(docencia.uuidModulo),
        docencia.curso.toInt(),
        typeGrade(docencia.grado)
        )
}