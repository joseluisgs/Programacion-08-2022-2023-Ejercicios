package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ModuloError
import models.Modulo

object ModuloValidator {
    fun validate(item: Modulo): Result<Modulo, ModuloError> {
        return when {
            item.uuid.toString().isBlank() -> Err(ModuloError.UuidInvalid("EL UUID no puede estar vacío"))
            item.name.isBlank() || item.name.length <= 3 -> Err(ModuloError.NameInvalid("El nombre no puede estar vacío y menor de 3 letras"))
            item.curso < 1 || item.curso > 2 -> Err(ModuloError.CursoInvalid("El curso sólo puede ser 1 o 2"))
            item.grado == Modulo.TypeGrado.NONE -> Err(ModuloError.GradoInvalid("El grado no puede ser NONE"))
            else -> Ok(item)
        }
    }
}