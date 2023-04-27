package validator.conductores

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.github.michaelbull.result.onFailure
import models.conductor.Conductor
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class ConductoresValidatorKtTest {

    val conductorUuid = UUID.fromString("9126fe9b-9a6a-45d8-a613-00313efc6d0e")

    @Test
    fun validate() {
        val conductoresValido = Conductor(
            uuid = conductorUuid,
            dni = "56748098D",
            nombre = "Ramón",
            apellidos = "Cajal Y Cajol",
            edad = 24
        )
        assertEquals(conductoresValido, conductoresValido.validate().get())
    }

    @Test
    fun validateButDniIncorrect() {
        val conductoresDniIncorrect = Conductor(
            uuid = conductorUuid,
            dni = "",
            nombre = "Ramón",
            apellidos = "Cajal Y Cajol",
            edad = 24
        )
        assertEquals("El dni: \"\", no es válido.", conductoresDniIncorrect.validate().getError()!!.message)
    }

    @Test
    fun validateButNameIncorrect() {
        val conductoresNombreIncorrecto = Conductor(
            uuid = conductorUuid,
            dni = "56748098D",
            nombre = "",
            apellidos = "Cajal Y Cajol",
            edad = 24
        )
        assertEquals("El nombre: \"\", no es válido.", conductoresNombreIncorrecto.validate().getError()!!.message)
    }

    @Test
    fun validateButLastnameIncorrect() {
        val conductoresLastnameIncorrect = Conductor(
            uuid = conductorUuid,
            dni = "56748098D",
            nombre = "Ramón",
            apellidos = "",
            edad = 24
        )
        assertEquals("Los apellidos: \"\", no son válidos.", conductoresLastnameIncorrect.validate().getError()!!.message)
    }

    @Test
    fun validateButAgeIncorrect() {
        val conductoresEdadIncorrect = Conductor(
            uuid = conductorUuid,
            dni = "56748098D",
            nombre = "Ramón",
            apellidos = "Cajal Y Cajol",
            edad = 12
        )
        assertEquals("La edad: 12, no puede ser menor que 18.", conductoresEdadIncorrect.validate().getError()!!.message)
    }
}