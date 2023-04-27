package validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import error.PersonaError
import models.Alumno
import models.Profesor

fun Alumno.validate(): Result<Boolean, PersonaError> {
    if (nombre.isEmpty()){
        return Err(PersonaError.NombreIncorrect(nombre))
    }
    val edadMin = 1
    if (edad < edadMin)  {
        return Err(PersonaError.EdadIncorrect(edad.toString(), edadMin.toString()))
    }
    return Ok(true)
}

fun Profesor.validate(): Result<Boolean, PersonaError> {
    if (nombre.isEmpty()){
        return Err(PersonaError.NombreIncorrect(nombre))
    }
    if (modulo.isEmpty())  {
        return Err(PersonaError.ModuloIncorrect(modulo))
    }
    return Ok(true)
}