package validators

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import errors.BurguerError
import models.Burguer
import models.Ingrediente
import models.LineaBurguer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class BurguerValidatorTest {

    private var lineasBurguer = mutableListOf<LineaBurguer>()
    private val newUUIDburguer: UUID = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
        lineasBurguer.add(LineaBurguer(0, newUUIDburguer,ingredeint.id, 2,ingredeint.price))
    }

    @Test
    fun validateCorrect() {
        val burguerToTest = Burguer(newUUIDburguer, "abcd",1, lineasBurguer)

        val result = BurguerValidator.validate(burguerToTest)
        assert(result.get() == burguerToTest)
    }

    @Test
    fun validateIncorrectName() {
        val burguerToTest = Burguer(newUUIDburguer, "",1, lineasBurguer)
        val result = BurguerValidator.validate(burguerToTest)

        assertAll(
            { assert(result.get() == null) },
            { assert(result.getError() is BurguerError.NameInvalid) },
            { assert(result.getError()!!.message == "El nombre no puede estar vacío y menor de 3 letras") },
        )
    }

    @Test
    fun validateIncorrectStock() {
        val burguerToTest = Burguer(newUUIDburguer, "abcd",-1, lineasBurguer)
        val result = BurguerValidator.validate(burguerToTest)

        val burguerToTest2 = Burguer(newUUIDburguer, "abcd",100, lineasBurguer)
        val result2 = BurguerValidator.validate(burguerToTest2)

        assertAll(
            { assert(result.get() == null) },
            { assert(result.getError() is BurguerError.StockInvalid) },
            { assert(result.getError()!!.message == "El stock no puede ser menor de 0 y mayor de 99") },
            { assert(result2.get() == null) },
            { assert(result2.getError() is BurguerError.StockInvalid) },
            { assert(result2.getError()!!.message == "El stock no puede ser menor de 0 y mayor de 99") }
        )
    }
}