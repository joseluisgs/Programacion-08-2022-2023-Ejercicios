package validators

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import errors.VehicleError
import models.Vehicle
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.util.*

class VehicleValidatorTest {

    @Test
    fun validateCorrect() {
        val vehicle = Vehicle(UUID.randomUUID(), "abcd", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID())
        val result = VehicleValidator.validate(vehicle)

        assert(result.get() == vehicle)
    }

    @Test
    fun exceptionIncorrectUUID() {
        assertThrows<IllegalArgumentException> {
            Vehicle(
                UUID.fromString(""),
                "abcd",
                Vehicle.TypeMotor.HIBRIDO,
                UUID.randomUUID()
            )
        }

        assertThrows<IllegalArgumentException> {
            Vehicle(
                UUID.randomUUID(),
                "abcd",
                Vehicle.TypeMotor.HIBRIDO,
                UUID.fromString("")
            )
        }
    }

    @Test
    fun validateIncorrectModel() {
        val vehicle = Vehicle(UUID.randomUUID(), "a", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID())
        val result = VehicleValidator.validate(vehicle)

        assertAll(
            { assert(result.get() == null) },
            { assert(result.getError() is VehicleError.ModelInvalid) },
            { assert(result.getError()!!.message == "El modelo no puede estar vacío y menor de 3 letras") },
        )
    }

    @Test
    fun validateIncorrectMotor() {
        val vehicle = Vehicle(UUID.randomUUID(), "abcd", Vehicle.TypeMotor.ERROR, UUID.randomUUID())
        val result = VehicleValidator.validate(vehicle)

        assertAll(
            { assert(result.get() == null) },
            { assert(result.getError() is VehicleError.MotorInvalid) },
            { assert(result.getError()!!.message == "El motor no puede estar vacío o hubo ERROR inesperado") },
        )
    }
}