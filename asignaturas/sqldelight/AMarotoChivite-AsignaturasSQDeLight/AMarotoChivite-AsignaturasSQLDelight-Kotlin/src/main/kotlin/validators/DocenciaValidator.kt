package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.DocenciaError
import models.Docencia
import models.Modulo

object DocenciaValidator {
    fun validate(item: Docencia): Result<Docencia, DocenciaError> {
        return when {
            item.idProfesor.toString()
                .isBlank() -> Err(DocenciaError.IdProfesorInvalid("EL ID del profesor no puede estar vacío"))

            item.uuidModulo.toString()
                .isBlank() -> Err(DocenciaError.UuidModuloInvalid("EL UUID del modulo no puede estar vacío"))

            item.curso < 1 || item.curso > 2 -> Err(DocenciaError.CursoInvalid("El curso sólo puede ser 1 o 2"))

            item.grado == Modulo.TypeGrado.NONE -> Err(DocenciaError.GradoInvalid("El grado no puede ser NONE"))

            else -> Ok(item)
        }
    }
}