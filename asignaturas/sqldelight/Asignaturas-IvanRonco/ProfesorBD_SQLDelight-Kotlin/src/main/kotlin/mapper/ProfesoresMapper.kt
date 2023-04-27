package mapper

import database.ProfesorEntity
import dto.ConjuntoProfesoresDto
import dto.ProfesorDto
import models.Profesor
import java.util.*

fun Profesor.toDto(): ProfesorDto{
    return ProfesorDto(
        this.uuid.toString(),
        this.nombre,
        this.experiencia.toString()
    )
}

fun ProfesorDto.toProfesor(): Profesor{
    return Profesor(
        uuid = UUID.fromString(this.uuid),
        nombre = this.nombre,
        experiencia = this.experiencia.toInt()
    )
}

fun List<Profesor>.toDtos(): ConjuntoProfesoresDto{
    return ConjuntoProfesoresDto(
        this.map { it.toDto() }
    )
}

fun ConjuntoProfesoresDto.toProfesores(): List<Profesor>{
    return this.profesores.map { it.toProfesor() }
}

fun ProfesorEntity.toProfesorFromEntity(): Profesor{
    return Profesor(
        this.id,
        UUID.fromString(this.uuid),
        this.nombre,
        this.experiencia.toInt()
    )
}