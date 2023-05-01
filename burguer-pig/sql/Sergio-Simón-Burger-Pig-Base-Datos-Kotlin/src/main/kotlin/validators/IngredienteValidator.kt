package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.IngredienteErrors
import models.Ingrediente

fun Ingrediente.validate(): Result<Ingrediente, IngredienteErrors> {
    return when {
        price <= 0 -> Err(IngredienteErrors.PrecioNoValido("Precio no valido"))
        cantidad <= 0 -> Err(IngredienteErrors.CantidadNoValida("Precio no valido"))
        else -> Ok(this)
    }
}
