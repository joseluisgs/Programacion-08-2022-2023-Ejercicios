package mapper.forSQLDelight

import database.AlumnosEntities
import database.ProfesoresEntities
import models.Alumno
import models.Profesor

fun ProfesoresEntities.toProfesor(): Profesor{
    return Profesor(
        id = this.ID,
        nombre = this.NOMBRE,
        modulo = this.MODULO
    )
}

fun AlumnosEntities.toAlumno(): Alumno{
    return Alumno(
        id = this.ID,
        nombre = this.NOMBRE,
        edad = this.EDAD.toInt()
    )
}