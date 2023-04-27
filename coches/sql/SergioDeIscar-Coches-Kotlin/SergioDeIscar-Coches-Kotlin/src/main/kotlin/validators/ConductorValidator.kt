package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getError
import errors.ConductorError
import models.Conductor
import java.time.LocalDate

fun Conductor.validate(): Result<Conductor, ConductorError>{
    return when{
        nombre.trim().isBlank() -> Err(ConductorError.ConductorNombreError())
        fechaCarnet.isAfter(LocalDate.now()) -> Err(ConductorError.ConductorFechaCarnetError())
        coches.any { it.validate().getError() != null } -> Err(ConductorError.ConductorCochesError())
        else -> Ok(this)
    }
}