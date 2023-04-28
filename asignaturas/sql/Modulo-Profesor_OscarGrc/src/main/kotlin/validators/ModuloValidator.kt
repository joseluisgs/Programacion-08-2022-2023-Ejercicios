package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import errors.ModuloErrors
import models.Modulo


fun Modulo.validate(): com.github.michaelbull.result.Result<Modulo,ModuloErrors> {
    if(nombre.isBlank()){
        return Err(ModuloErrors.NombreVacio("El nombre no puede estar vacío"))
    }
    if(curso.isBlank()){
        return Err(ModuloErrors.NombreVacio("El nombre no puede estar vacío"))
    }
    return Ok(this)
}



