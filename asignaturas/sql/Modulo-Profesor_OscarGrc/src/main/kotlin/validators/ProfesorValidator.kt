package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import errors.ProfesorErrors
import models.Profesor


fun Profesor.validate(): com.github.michaelbull.result.Result<Profesor,ProfesorErrors> {
    if(nombre.isBlank()){
        return Err(ProfesorErrors.ProfesorNoEncontrado("El nombre no puede estar vacio "))
    }
    return Ok(this)
}



