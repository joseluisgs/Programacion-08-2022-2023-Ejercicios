package validator

import exceptions.ProfesorException
import models.Profesor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class ValidatorKtTest{

    @Test
    fun validatorCorrect(){
        val profesorCorrecto = Profesor(
            id = 1,
            uuid = UUID.randomUUID(),
            nombre = "Manuel",
            experiencia = 12
        )
        assertTrue(profesorCorrecto.validate())
    }

    @Test
    fun validatorNameIncorrect() {
        val profesorConNobreIncorrecto = Profesor(
            id = 1,
            uuid = UUID.randomUUID(),
            nombre = "",
            experiencia = 12
        )

        val message = assertThrows<ProfesorException.NombreNoValido> {
            profesorConNobreIncorrecto.validate()
        }.message
        assertEquals(
            """El nombre: "", no es válido.""",
            message
        )
    }

    @Test
    fun validatorExperienceIncorrect(){
        val profesorConExperienciaIncorrecta = Profesor(
            id = 1,
            uuid = UUID.randomUUID(),
            nombre = "Jorge",
            experiencia = -2
        )

        val message = assertThrows<ProfesorException.ExperienciasNoValida> {
            profesorConExperienciaIncorrecta.validate()
        }.message
        assertEquals(
            "El precio: -2, no puede ser menor que 0.",
            message
        )
    }

}