package validator

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import models.Alumno
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AlumnoValidatorKtTest {

    @Test
    fun validate() {
        val alumnoValido = Alumno(
            1L,
            "Juan",
            23
        )
        assertTrue(alumnoValido.validate().get()!!)
    }

    @Test
    fun validateButNombreIncorrect() {
        val alumnoNombreInvalido = Alumno(
            1L,
            "",
            23
        )
        assertEquals("El nombre: \"\", no es válido.", alumnoNombreInvalido.validate().getError()!!.message)
    }

    @Test
    fun validateButEdadIncorrect() {
        val alumnoEdadInvalida = Alumno(
            1L,
            "Jose",
            -23
        )
        assertEquals("La edad: \"-23\", no es válida, no puede ser menor que: 1.", alumnoEdadInvalida.validate().getError()!!.message)
    }
}