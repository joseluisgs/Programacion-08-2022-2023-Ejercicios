package validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import error.IngredienteError
import models.Ingrediente
import java.lang.IllegalArgumentException

fun Ingrediente.validate(): Result<Ingrediente, IngredienteError>{
    if(this.nombre.isEmpty()){
        return Err(IngredienteError.NombreNoValido(nombre))
    }
    if(this.precio < 0.0){
        return Err(IngredienteError.PrecioNoValido(precio.toString(), "0.0"))
    }
    if(this.cantidad < 0){
        return Err(IngredienteError.CantidadNoValido(cantidad.toString(), "0"))
    }
    return Ok(this)
}