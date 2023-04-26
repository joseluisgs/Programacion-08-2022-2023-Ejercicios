package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.BurguerError
import models.Burguer

object BurguerValidator {
    fun validate(item: Burguer): Result<Burguer, BurguerError> {
        return when {
            item.uuid.toString().isBlank() -> Err(BurguerError.UuidInvalid("EL UUID no puede estar vacío"))
            item.name.isBlank() || item.name.length <= 3 -> Err(BurguerError.NameInvalid("El nombre no puede estar vacío y menor de 3 letras"))
            item.stock < 0 ||  item.stock > 99 -> Err(BurguerError.StockInvalid("El stock no puede ser menor de 0 y mayor de 99"))
            else -> Ok(item)
        }
    }
}