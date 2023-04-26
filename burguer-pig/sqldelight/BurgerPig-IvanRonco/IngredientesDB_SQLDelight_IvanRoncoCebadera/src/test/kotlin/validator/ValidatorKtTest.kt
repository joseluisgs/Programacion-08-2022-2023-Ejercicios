package validator

import com.github.michaelbull.result.*
import error.IngredienteError
import models.Ingrediente
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class ValidatorKtTest{

    @Test
    fun validatorCorrect(){
        val ingredienteCorrecto = Ingrediente(
            id = 1,
            uuid = UUID.randomUUID(),
            nombre = "Carne",
            precio = 2.75,
            cantidad = 278
        )
        assertEquals(Ok(ingredienteCorrecto), ingredienteCorrecto.validate())
    }

    @Test
    fun validatorNameIncorrect(){
        val ingredienteConNobreIncorrecto = Ingrediente(
            id = 1,
            uuid = UUID.randomUUID(),
            nombre = "",
            precio = 2.75,
            cantidad = 278
        )
        assertEquals(
            //El mensaje de error debe ser:
            //"El nombre , no es válido."
            Err(IngredienteError.NombreNoValido("")).error.message,
            ingredienteConNobreIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorPriceIncorrect(){
        val ingredienteConPrecioIncorrecto = Ingrediente(
            id = 1,
            uuid = UUID.randomUUID(),
            nombre = "Carne",
            precio = -3.67,
            cantidad = 278
        )
        assertEquals(
            //El mensaje de error debe ser:
            //"El precio: -3.67, no puede ser menor que 0.0."
            Err(IngredienteError.PrecioNoValido(ingredienteConPrecioIncorrecto.precio.toString(), "0.0")).error.message,
            ingredienteConPrecioIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorCantidadIncorrect(){
        val ingredienteConCantidadIncorrecto = Ingrediente(
            id = 1,
            uuid = UUID.randomUUID(),
            nombre = "Carne",
            precio = 7.65,
            cantidad = -143
        )
        assertEquals(
            //El mensaje de error debe ser:
            //"La cantidad: -143, no puede ser menor que 0."
            Err(IngredienteError.CantidadNoValido(ingredienteConCantidadIncorrecto.cantidad.toString(), "0")).error.message,
            ingredienteConCantidadIncorrecto.validate().getError()!!.message
        )
    }

}