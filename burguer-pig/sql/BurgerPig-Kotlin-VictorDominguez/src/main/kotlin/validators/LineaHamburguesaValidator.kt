package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.LineaHamburguesaException
import exceptions.LineaHamburguesaNoValidaException
import models.LineaHamburguesa

fun LineaHamburguesa.validar(): Result<LineaHamburguesa, LineaHamburguesaException> {
    require(this.id_ham.toString().isNotBlank()) { return Err(LineaHamburguesaNoValidaException("La linea debe incluir el ID de la hamburguesa")) }
    require(this.id_ing.toString().isNotBlank()) { return Err(LineaHamburguesaNoValidaException("La linea debe incluir el ID del ingrediente")) }
    require(this.cantidad >= 1) { return Err(LineaHamburguesaNoValidaException("La cantidad debe ser mínimo 1")) }
    return Ok(this)
}