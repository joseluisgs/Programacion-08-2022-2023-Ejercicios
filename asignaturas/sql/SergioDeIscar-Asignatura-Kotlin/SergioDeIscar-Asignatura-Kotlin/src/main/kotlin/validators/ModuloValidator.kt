package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ModuloError
import models.Modulo

fun Modulo.validate(): Result<Modulo, ModuloError>{
    return when{
        nombre.trim().isBlank() -> Err(ModuloError.ModuloNombreError())
        curso !in 1..2 -> Err(ModuloError.ModuloCursoError())
        else -> Ok(this)
    }
}