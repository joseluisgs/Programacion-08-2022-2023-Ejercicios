package validators

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import errors.ConductorError
import models.Coche
import models.Conductor
import models.TypeMotor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import java.util.UUID

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConductorValidatorTest {
    val cochesOk = listOf(
        Coche(
            1,
            "Seat",
            "Ibiza",
            12000f,
            TypeMotor.DIESEL,
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e")
        )
    )

    val cochesFail = listOf(
        Coche(
            -1,
            "Seat",
            "Ibiza",
            12000f,
            TypeMotor.DIESEL,
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e")
        )
    )

    @Test
    fun validateConductorOk(){
        val conductorOk = Conductor(
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e"),
            "Pepe",
            LocalDate.parse("2020-01-01"),
            cochesOk
        )
        assertAll(
            { assert(conductorOk.validate().getError() == null) },
            { assert(conductorOk.validate().get() == conductorOk) }
        )
    }

    @Test
    fun validateConductorNombreFail(){
        val conductorNombreError = Conductor(
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e"),
            "",
            LocalDate.parse("2020-01-01"),
            cochesOk
        )
        assertAll(
            { assert(conductorNombreError.validate().getError() != null) },
            { assert(conductorNombreError.validate().getError() is ConductorError.ConductorNombreError) }
        )
    }

    @Test
    fun validateConductorFechaCarnetFail(){
        val conductorFechaCarnetError = Conductor(
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e"),
            "Pepe",
            LocalDate.parse("2050-01-01"),
            cochesOk
        )
        assertAll(
            { assert(conductorFechaCarnetError.validate().getError() != null) },
            { assert(conductorFechaCarnetError.validate().getError() is ConductorError.ConductorFechaCarnetError) }
        )
    }

    @Test
    fun validateConductorCochesFail(){
        val conductorCochesError = Conductor(
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e"),
            "Pepe",
            LocalDate.parse("2020-01-01"),
            cochesFail
        )
        assertAll(
            { assert(conductorCochesError.validate().getError() != null) },
            { assert(conductorCochesError.validate().getError() is ConductorError.ConductorCochesError) }
        )
    }
}