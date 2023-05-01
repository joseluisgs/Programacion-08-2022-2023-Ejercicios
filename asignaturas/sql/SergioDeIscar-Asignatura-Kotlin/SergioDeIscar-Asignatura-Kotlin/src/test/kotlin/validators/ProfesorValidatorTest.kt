package validators

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import errors.ProfesorError
import models.Grado
import models.Modulo
import models.Profesor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfesorValidatorTest {
    private val modulo = Modulo(
        UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e"),
        "Programación",
        1,
        Grado.DAM
    )

    @Test
    fun validateProfesorOk(){
        val profesorOk = Profesor(
            1,
            "Pepe",
            LocalDate.parse("2009-11-20"),
            listOf(modulo)
        )
        assertAll(
            { assert(profesorOk.validate().getError() == null) },
            { assert(profesorOk.validate().get() == profesorOk) }
        )
    }

    @Test
    fun validateProfesorIdFail(){
        val profesorIdError = Profesor(
            -1,
            "Pepe",
            LocalDate.parse("2009-11-20"),
            listOf(modulo)
        )
        assertAll(
            { assert(profesorIdError.validate().getError() != null) },
            { assert(profesorIdError.validate().getError() is ProfesorError.ProfesorIdError) }
        )
    }

    @Test
    fun validateProfesorNombreFail(){
        val profesorNombreError = Profesor(
            1,
            "",
            LocalDate.parse("2009-11-20"),
            listOf(modulo)
        )
        assertAll(
            { assert(profesorNombreError.validate().getError() != null) },
            { assert(profesorNombreError.validate().getError() is ProfesorError.ProfesorNombreError) }
        )
    }

    @Test
    fun validateProfesorFechaFail(){
        val profesorNombreError = Profesor(
            1,
            "Pepe",
            LocalDate.parse("2027-11-20"),
            listOf(modulo)
        )
        assertAll(
            { assert(profesorNombreError.validate().getError() != null) },
            { assert(profesorNombreError.validate().getError() is ProfesorError.ProfesorFechaIncorporacionError) }
        )
    }
}