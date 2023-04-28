package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import errors.CochesErrors
import errors.ConductorErrors
import models.Coche
import models.Conductor


fun Conductor.validate(): com.github.michaelbull.result.Result<Conductor,ConductorErrors> {
    if(nombre.isBlank()){
        return Err(ConductorErrors.NombreVacio("El nombre no puede estar vacío"))
    }
    return Ok(this)
}



