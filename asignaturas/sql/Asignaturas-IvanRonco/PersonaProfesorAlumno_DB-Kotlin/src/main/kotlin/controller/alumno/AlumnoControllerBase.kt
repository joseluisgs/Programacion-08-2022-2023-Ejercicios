package controller.alumno

import com.github.michaelbull.result.Result
import controller.base.CrudController
import error.PersonaError
import models.Alumno

interface AlumnoControllerBase: CrudController<Alumno, Long, PersonaError> {
    fun findByEdad(edad: Int): List<Alumno>
}