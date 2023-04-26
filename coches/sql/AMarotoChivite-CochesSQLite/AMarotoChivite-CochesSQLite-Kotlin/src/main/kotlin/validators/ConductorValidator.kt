package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ConductorError
import models.Conductor

object ConductorValidator {
    fun validate(conductor: Conductor): Result<Conductor, ConductorError> {
        return when {
            conductor.uuid.toString().isBlank() -> Err(ConductorError.UuidInvalid("El UUID no puede estar vacío"))
            conductor.name.isBlank() || conductor.name.length <= 3 -> Err(ConductorError.NameInvalid("El nombre no puede estar vacío y menor de 3 letras"))
            else -> Ok(conductor)
        }
    }
}