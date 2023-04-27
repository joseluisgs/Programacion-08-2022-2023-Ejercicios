package controllers

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import controllers.coche.CocheController
import errors.CocheError
import factories.CocheFactory.getCochesDefault
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import repositories.coche.CocheRepositoryMap
import services.storage.coche.CocheFileJson
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class CocheControllerTest {
    @MockK
    private lateinit var storage: CocheFileJson
    @MockK
    private lateinit var repo: CocheRepositoryMap

    @InjectMockKs
    private lateinit var controller: CocheController

    @Test
    fun findAllTest(){
        every { repo.findAll() } returns getCochesDefault()
        assertEquals(
            getCochesDefault().size,
            controller.findAll().toList().size
        )
        verify { repo.findAll() }
    }

    @Test
    fun findByIdTest(){
        every { repo.findById(0) } returns getCochesDefault()[0]
        every { repo.findById(5) } returns null
        assertAll(
            { assertEquals(controller.findById(0).get(), getCochesDefault()[0]) },
            { assertTrue { controller.findById(5).getError() is CocheError.CocheNoEncontradoError } }
        )
        verify { repo.findById(0) }
        verify { repo.findById(5) }
    }

    @Test
    fun saveTest(){
        val cocheIdError = getCochesDefault()[0].copy(id = -1)
        val cocheMarcaError = getCochesDefault()[0].copy(marca = "")
        val cocheModeloError = getCochesDefault()[0].copy(modelo = "")
        val cochePrecioError = getCochesDefault()[0].copy(precio = -1.0f)
        every { repo.save(getCochesDefault()[0]) } returns getCochesDefault()[0]
        assertAll(
            { assertEquals(controller.save(getCochesDefault()[0]).get(), getCochesDefault()[0]) },
            { assertTrue { controller.save(cocheIdError).getError() is CocheError.CocheIdError } },
            { assertTrue { controller.save(cocheMarcaError).getError() is CocheError.CocheMarcaError } },
            { assertTrue { controller.save(cocheModeloError).getError() is CocheError.CocheModeloError } },
            { assertTrue { controller.save(cochePrecioError).getError() is CocheError.CochePrecioError } }
        )
        verify { repo.save(getCochesDefault()[0]) }
    }

    @Test
    fun saveAllTest(){
        val cochesIdError = listOf(getCochesDefault()[0].copy(id = -1))
        val cochesMarcaError = listOf(getCochesDefault()[0].copy(marca = ""))
        val cochesModeloError = listOf(getCochesDefault()[0].copy(modelo = ""))
        val cochesPrecioError = listOf(getCochesDefault()[0].copy(precio = -1.0f))
        every { repo.saveAll(getCochesDefault()) } returns Unit
        assertAll(
            { assertTrue {controller.saveAll(getCochesDefault()).get() != null } },
            { assertTrue { controller.saveAll(cochesIdError).getError() is CocheError.CocheIdError } },
            { assertTrue { controller.saveAll(cochesMarcaError).getError() is CocheError.CocheMarcaError } },
            { assertTrue { controller.saveAll(cochesModeloError).getError() is CocheError.CocheModeloError } },
            { assertTrue { controller.saveAll(cochesPrecioError).getError() is CocheError.CochePrecioError } }
        )
        verify { repo.saveAll(getCochesDefault()) }
    }

    @Test
    fun deleteByIdTest(){
        every { repo.deleteById(0) } returns true
        every { repo.deleteById(5) } returns false
        assertAll(
            { assertTrue { controller.deleteById(0).get() != null } },
            { assertTrue { controller.deleteById(5).getError() is CocheError.CocheNoEncontradoError } }
        )
        verify { repo.deleteById(0) }
        verify { repo.deleteById(5) }
    }

    @Test
    fun deleteTest(){
        every { repo.delete(getCochesDefault()[0]) } returns true
        every { repo.delete(getCochesDefault()[1]) } returns false
        assertAll(
            { assertTrue { controller.delete(getCochesDefault()[0]).get() != null } },
            { assertTrue { controller.delete(getCochesDefault()[1]).getError() is CocheError.CocheNoEncontradoError } }
        )
        verify { repo.delete(getCochesDefault()[0]) }
        verify { repo.delete(getCochesDefault()[1]) }
    }

    @Test
    fun existsByIdTest(){
        every { repo.existsById(0) } returns true
        every { repo.existsById(5) } returns false
        assertAll(
            { assertTrue { controller.existsById(0).get() != null } },
            { assertTrue { controller.existsById(5).getError() is CocheError.CocheNoEncontradoError } }
        )
        verify { repo.existsById(0) }
        verify { repo.existsById(5) }
    }
}