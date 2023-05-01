package validators

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import errors.CocheError
import models.Coche
import models.TypeMotor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CocheValidatorTest {
    @Test
    fun validateCocheOk(){
        val cocheOk = Coche(
            1,
            "Seat",
            "Ibiza",
            12000f,
            TypeMotor.DIESEL,
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e")
        )
        assertAll(
            { assert(cocheOk.validate().getError() == null) },
            { assert(cocheOk.validate().get() == cocheOk) }
        )
    }

    @Test
    fun validateCocheIdFail(){
        val cocheIdError = Coche(
            -1,
            "Seat",
            "Ibiza",
            12000f,
            TypeMotor.DIESEL,
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e")
        )
        assertAll(
            { assert(cocheIdError.validate().getError() != null) },
            { assert(cocheIdError.validate().getError() is CocheError.CocheIdError) }
        )
    }

    @Test
    fun validateCocheMarcaFail(){
        val cocheMarcaError = Coche(
            1,
            "",
            "Ibiza",
            12000f,
            TypeMotor.DIESEL,
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e")
        )
        assertAll(
            { assert(cocheMarcaError.validate().getError() != null) },
            { assert(cocheMarcaError.validate().getError() is CocheError.CocheMarcaError) }
        )
    }

    @Test
    fun validateCocheModeloFail(){
        val cocheModeloError = Coche(
            1,
            "Seat",
            "",
            12000f,
            TypeMotor.DIESEL,
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e")
        )
        assertAll(
            { assert(cocheModeloError.validate().getError() != null) },
            { assert(cocheModeloError.validate().getError() is CocheError.CocheModeloError) }
        )
    }

    @Test
    fun validateCochePrecioFail(){
        val cochePrecioError = Coche(
            1,
            "Seat",
            "Ibiza",
            -1f,
            TypeMotor.DIESEL,
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e")
        )
        assertAll(
            { assert(cochePrecioError.validate().getError() != null) },
            { assert(cochePrecioError.validate().getError() is CocheError.CochePrecioError) }
        )
    }
}