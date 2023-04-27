package validator.motores

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import models.motor.MotorDiesel
import models.motor.MotorElectrico
import models.motor.MotorHibrido
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class MotorValidatorKtTest {

    @Test
    fun validate() {
        val motorCorrecto = MotorDiesel(
            uuid = UUID.fromString("37f229b0-4ea2-4e69-a37b-1e50de5fef2d"),
            modelo = "Toyota",
            caballos = 123,
            cilindradaDiesel = 8
        )
        assertEquals(motorCorrecto, motorCorrecto.validate().get())
    }

    @Test
    fun validateButModeloIncorrect() {
        val motorCorrecto = MotorDiesel(
            uuid = UUID.fromString("37f229b0-4ea2-4e69-a37b-1e50de5fef2d"),
            modelo = "",
            caballos = 123,
            cilindradaDiesel = 8
        )
        assertEquals("""El modelo: "", no es válido.""", motorCorrecto.validate().getError()!!.message)
    }

    @Test
    fun validateButCaballosIncorrect() {
        val motorCorrecto = MotorDiesel(
            uuid = UUID.fromString("37f229b0-4ea2-4e69-a37b-1e50de5fef2d"),
            modelo = "Toyota",
            caballos = -123,
            cilindradaDiesel = 8
        )
        assertEquals("Los caballos: -123, no pueden ser menor que 0.", motorCorrecto.validate().getError()!!.message)
    }

    @Test
    fun validateButCilindradaIncorrect() {
        val motorCorrecto = MotorDiesel(
            uuid = UUID.fromString("37f229b0-4ea2-4e69-a37b-1e50de5fef2d"),
            modelo = "Toyota",
            caballos = 123,
            cilindradaDiesel = -8
        )
        assertEquals("La cilindrada: -8, no puede ser menor que 0.", motorCorrecto.validate().getError()!!.message)
    }

    @Test
    fun validateButPorcentajeCargaIncorrect() {
        val motorCorrecto = MotorElectrico(
            uuid = UUID.fromString("37f229b0-4ea2-4e69-a37b-1e50de5fef2d"),
            modelo = "Toyota",
            caballos = 123,
            porcentajeCargado = -34.9
        )
        assertEquals("El porcentaje de carga: -34.9%, no puede ser menor que 0.0%.", motorCorrecto.validate().getError()!!.message)
    }

    @Test
    fun validateButCapacidadIncorrect() {
        val motorCorrecto = MotorHibrido(
            uuid = UUID.fromString("37f229b0-4ea2-4e69-a37b-1e50de5fef2d"),
            modelo = "Toyota",
            caballos = 123,
            capacidadElectrica = -34.9,
            capacidadGasolina = -54.3
        )
        assertEquals("La capacidad: -34.9, no puede ser menor que 0.0.", motorCorrecto.validate().getError()!!.message)
    }
}