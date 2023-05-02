package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.HamburguesaException
import exceptions.HamburguesaNoValidaException
import models.Hamburguesa

fun Hamburguesa.validar(): Result<Hamburguesa, HamburguesaException> {
    require(this.nombre.isNotBlank()) { return Err(HamburguesaNoValidaException("La hamburguesa debe tener nombre")) }
    require(this.precio >= 0.0) { return Err(HamburguesaNoValidaException("El precio no puede ser negativo")) }
    return Ok(this)
}