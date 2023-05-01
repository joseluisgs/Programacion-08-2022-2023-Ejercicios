package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ProfesorError
import models.Profesor
import java.time.LocalDate

object ProfesorValidator {
    fun validate(item: Profesor): Result<Profesor, ProfesorError> {
        return when {
            item.id.toString().isBlank() -> Err(ProfesorError.IdInvalid("EL id no puede estar vacío"))
            item.name.isBlank() || item.name.length <= 3 -> Err(ProfesorError.NameInvalid("El nombre no puede estar vacío y menor de 3 letras"))
            item.dateInit.toString()
                .isBlank() || item.dateInit.isBefore(LocalDate.now()) -> Err(ProfesorError.DateInitInvalid("La fecha no puede estar vacía o anterior a la fecha de hoy"))

            else -> Ok(item)
        }
    }
}