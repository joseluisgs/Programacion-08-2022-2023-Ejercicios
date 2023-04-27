package models

import java.util.*

data class Docencia(
    val idProfesor: Long,
    val uuidModulo: UUID,
    val curso: Int,
    val grado: Modulo.TypeGrado
)