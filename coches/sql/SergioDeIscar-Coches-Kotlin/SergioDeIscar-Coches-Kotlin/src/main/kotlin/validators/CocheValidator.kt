package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.CocheError
import models.Coche

fun Coche.validate(): Result<Coche, CocheError>{
    return when{
        id < 0 -> Err(CocheError.CocheIdError())
        marca.trim().isBlank() -> Err(CocheError.CocheMarcaError())
        modelo.trim().isBlank() -> Err(CocheError.CocheModeloError())
        precio < 0 -> Err(CocheError.CochePrecioError())
        else -> Ok(this)
    }
}