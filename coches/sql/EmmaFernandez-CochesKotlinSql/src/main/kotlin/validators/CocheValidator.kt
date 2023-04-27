package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.CocheError
import models.Coche

// Validador de coches
fun Coche.validate(): Result<Coche, CocheError> {
    return when (conductor) {
        else -> Ok(this)
    }
}
