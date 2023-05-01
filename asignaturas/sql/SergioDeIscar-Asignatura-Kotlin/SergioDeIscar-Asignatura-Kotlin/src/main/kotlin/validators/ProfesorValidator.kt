package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ProfesorError
import models.Profesor
import java.time.LocalDate

fun Profesor.validate(): Result<Profesor, ProfesorError>{
    return when{
        id < 0 -> Err(ProfesorError.ProfesorIdError())
        nombre.trim().isBlank() -> Err(ProfesorError.ProfesorNombreError())
        fechaIncorporacion.isAfter(LocalDate.now()) -> Err(ProfesorError.ProfesorFechaIncorporacionError())
        else -> Ok(this)
    }
}