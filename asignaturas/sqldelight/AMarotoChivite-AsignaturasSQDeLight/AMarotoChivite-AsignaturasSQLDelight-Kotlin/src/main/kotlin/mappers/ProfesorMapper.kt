package mappers

import database.ProfesorTable
import models.Profesor
import java.time.LocalDate
import java.util.*

fun toProfesor(prof: ProfesorTable): Profesor {
    return Profesor(
        prof.id.toLong(),
        prof.name,
        LocalDate.parse(prof.dateInit)
    )
}