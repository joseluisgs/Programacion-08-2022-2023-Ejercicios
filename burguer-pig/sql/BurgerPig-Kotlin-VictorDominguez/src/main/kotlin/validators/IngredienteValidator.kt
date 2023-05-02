package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.IngredienteException
import exceptions.IngredienteNoValidoException
import models.Ingrediente

fun Ingrediente.validar(): Result<Ingrediente, IngredienteException> {
    require(this.nombre.isNotBlank()) { return Err(IngredienteNoValidoException("El ingrediente debe tener nombre")) }
    require(this.precio >= 0.0) { return Err(IngredienteNoValidoException("El precio no puede ser negativo")) }
    return Ok(this)
}