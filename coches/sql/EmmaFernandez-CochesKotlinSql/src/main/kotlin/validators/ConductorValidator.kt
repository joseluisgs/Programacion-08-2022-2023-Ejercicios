package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ConductorError
import models.Conductor
import java.time.LocalDate

fun Conductor.validate(): Result<Conductor, ConductorError> {
    return when {
        fechaCarnet > LocalDate.now() -> Err(
            ConductorError.InvalidConductor("La fecha de carnet es posterior a la actual")
        )
        else -> Ok(this)
    }
}
