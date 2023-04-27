package controllers

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import controllers.coche.CocheController
import controllers.conductor.ConductorController
import errors.CocheError
import errors.ConductorError
import factories.CocheFactory.getCochesDefault
import factories.ConductorFactory.getConductorDefault
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
import repositories.conductor.ConductorRepositoryDataBase
import services.storage.coche.CocheFileJson
import services.storage.conductor.ConductorFileCsv
import java.time.LocalDate
import java.util.UUID
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class ConductorControllerTest {
    @MockK
    private lateinit var storage: ConductorFileCsv
    @MockK
    private lateinit var repo: ConductorRepositoryDataBase

    @InjectMockKs
    private lateinit var controller: ConductorController

    private val uuidOk = UUID.fromString("00000000-0000-0000-0000-000000000000")
    private val uuidError = UUID.fromString("00000000-0000-0000-0000-000000000001")

    @Test
    fun findAllTest(){
        every { repo.findAll() } returns getConductorDefault()
        assertEquals(
            getCochesDefault().size,
            controller.findAll().toList().size
        )
        verify { repo.findAll() }
    }

    @Test
    fun findByIdTest(){
        every { repo.findById(uuidOk) } returns getConductorDefault()[0]
        every { repo.findById(uuidError) } returns null
        assertAll(
            { assertEquals(controller.findById(uuidOk).get(), getConductorDefault()[0]) },
            { assertTrue { controller.findById(uuidError).getError() is ConductorError.ConductorNoEncontradoError } }
        )
        verify { repo.findById(uuidOk) }
        verify { repo.findById(uuidError) }
    }

    @Test
    fun saveTest(){
        val conductorNombreError = getConductorDefault()[0].copy(nombre = "")
        val conductorFechaCarnetError = getConductorDefault()[0].copy(fechaCarnet = LocalDate.parse("2050-01-01"))
        every { repo.save(getConductorDefault()[0]) } returns getConductorDefault()[0]
        assertAll(
            { assertEquals(controller.save(getConductorDefault()[0]).get(), getConductorDefault()[0]) },
            { assertTrue { controller.save(conductorNombreError).getError() is ConductorError.ConductorNombreError } },
            { assertTrue { controller.save(conductorFechaCarnetError).getError() is ConductorError.ConductorFechaCarnetError } }
        )
        verify { repo.save(getConductorDefault()[0]) }
    }

    @Test
    fun saveAllTest(){
        val conductoresNombreError = listOf(
            getConductorDefault()[0],
            getConductorDefault()[1].copy(nombre = "")
        )
        val conductoresFechaCarnetError = listOf(
            getConductorDefault()[0],
            getConductorDefault()[1].copy(fechaCarnet = LocalDate.parse("2050-01-01"))
        )
        every { repo.saveAll(getConductorDefault()) } returns Unit
        assertAll(
            { assertTrue { controller.saveAll(getConductorDefault()).get() != null } },
            { assertTrue { controller.saveAll(conductoresNombreError).getError() is ConductorError.ConductorNombreError } },
            { assertTrue { controller.saveAll(conductoresFechaCarnetError).getError() is ConductorError.ConductorFechaCarnetError } }
        )
        verify { repo.saveAll(getConductorDefault()) }
    }

    @Test
    fun deleteByIdTest(){
        every { repo.deleteById(uuidOk) } returns true
        every { repo.deleteById(uuidError) } returns false
        assertAll(
            { assertTrue { controller.deleteById(uuidOk).get() != null } },
            { assertTrue { controller.deleteById(uuidError).getError() is ConductorError.ConductorNoEncontradoError } }
        )
        verify { repo.deleteById(uuidOk) }
        verify { repo.deleteById(uuidError) }
    }

    @Test
    fun deleteTest(){
        every { repo.delete(getConductorDefault()[0]) } returns true
        every { repo.delete(getConductorDefault()[1]) } returns false
        assertAll(
            { assertTrue { controller.delete(getConductorDefault()[0]).get() != null } },
            { assertTrue { controller.delete(getConductorDefault()[1]).getError() is ConductorError.ConductorNoEncontradoError } }
        )
        verify { repo.delete(getConductorDefault()[0]) }
        verify { repo.delete(getConductorDefault()[1]) }
    }

    @Test
    fun existsByIdTest(){
        every { repo.existsById(uuidOk) } returns true
        every { repo.existsById(uuidError) } returns false
        assertAll(
            { assertTrue { controller.existsById(uuidOk).get() != null } },
            { assertTrue { controller.existsById(uuidError).getError() is ConductorError.ConductorNoEncontradoError } }
        )
        verify { repo.existsById(uuidOk) }
        verify { repo.existsById(uuidError) }
    }
}