package controller.profesor

import com.github.michaelbull.result.Result
import controller.base.CrudController
import error.PersonaError
import models.Profesor

interface ProfesorControllerBase: CrudController<Profesor, Long, PersonaError> {
    fun findByModulo(modulo: String): List<Profesor>
}