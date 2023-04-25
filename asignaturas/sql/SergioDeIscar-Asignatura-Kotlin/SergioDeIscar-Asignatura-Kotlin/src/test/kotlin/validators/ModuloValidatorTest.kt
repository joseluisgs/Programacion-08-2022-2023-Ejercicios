package validators

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import errors.ModuloError
import models.Grado
import models.Modulo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModuloValidatorTest {
    @Test
    fun validateModuloOk(){
        val moduloOk = Modulo(
            UUID.fromString("686e5f54-c0fc-40b5-ab88-a2b675a3c09e"),
            "Programación",
            1,
            Grado.DAM
        )
        assertAll(
            { assert(moduloOk.validate().getError() == null) },
            { assert(moduloOk.validate().get() == moduloOk) }
        )
    }

    @Test
    fun validateModuloNombreFail(){
        val moduloNombreError = Modulo(
            UUID.fromString("68c00de6-1917-4508-999b-0c7ca365268e"),
            "",
            1,
            Grado.DAM
        )
        assertAll(
            { assert(moduloNombreError.validate().getError() != null) },
            { assert(moduloNombreError.validate().getError() is ModuloError.ModuloNombreError) }
        )
    }

    @Test
    fun validateModuloCursoFail(){
        val moduloCursoError = Modulo(
            UUID.fromString("1f18582d-3efc-4888-b446-ca49ac2bdef8"),
            "Programación",
            3,
            Grado.DAM
        )
        assertAll(
            { assert(moduloCursoError.validate().getError() != null) },
            { assert(moduloCursoError.validate().getError() is ModuloError.ModuloCursoError) }
        )
    }
}