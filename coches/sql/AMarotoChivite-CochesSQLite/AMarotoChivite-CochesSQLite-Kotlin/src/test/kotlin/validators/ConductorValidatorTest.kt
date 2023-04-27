package validators

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import errors.ConductorError

import models.Conductor

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.util.*

class ConductorValidatorTest {

    @Test
    fun validateCorrect() {
        val conductor = Conductor(UUID.randomUUID(), "abcd")
        val result = ConductorValidator.validate(conductor)

        assert(result.get() == conductor)
    }

    @Test
    fun exceptionIncorrectUUID() {
        assertThrows<IllegalArgumentException> { Conductor(UUID.fromString(""), "abcd") }
    }

    @Test
    fun validateIncorrectName() {
        val conductor = Conductor(UUID.randomUUID(), "a")
        val result = ConductorValidator.validate(conductor)

        val conductor2 = Conductor(UUID.randomUUID(), "")
        val result2 = ConductorValidator.validate(conductor2)

        assertAll(
            { assert(result.get() == null) },
            { assert(result.getError() is ConductorError.NameInvalid) },
            { assert(result.getError()!!.message == "El nombre no puede estar vacío y menor de 3 letras") },
            { assert(result2.get() == null) },
            { assert(result2.getError() is ConductorError.NameInvalid) },
            { assert(result2.getError()!!.message == "El nombre no puede estar vacío y menor de 3 letras") }
        )
    }
}