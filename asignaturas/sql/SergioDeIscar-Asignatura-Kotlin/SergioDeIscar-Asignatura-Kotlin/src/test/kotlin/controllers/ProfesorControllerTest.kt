package controllers

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import controllers.profesor.ProfesorController
import errors.ProfesorError
import factories.ProfesorFactory.getProfesoresDefault
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
import repositories.profesor.ProfesorRepositoryMap
import services.storage.profesor.ProfesorFileCsv
import java.time.LocalDate
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class ProfesorControllerTest {
    @MockK
    private lateinit var storage: ProfesorFileCsv
    @MockK
    private lateinit var repo: ProfesorRepositoryMap

    @InjectMockKs
    private lateinit var controller: ProfesorController

    @Test
    fun findAllTest(){
        every { repo.findAll() } returns getProfesoresDefault()
        assertEquals(
            getProfesoresDefault().size,
            controller.findAll().toList().size
        )
        verify { repo.findAll() }
    }

    @Test
    fun findById(){
        every { repo.findById(0) } returns getProfesoresDefault()[0]
        every { repo.findById(5) } returns null
        assertAll(
            { assertEquals(controller.findById(0).get(), getProfesoresDefault()[0]) },
            { assertTrue { controller.findById(5).getError() is ProfesorError.ProfesorNoEncontradoError } }
        )
        verify { repo.findById(0) }
        verify { repo.findById(5) }
    }

    @Test
    fun saveTest(){
        val profesorIdError = getProfesoresDefault()[0].copy(id = -1)
        val profesorNombreError = getProfesoresDefault()[0].copy(nombre = "")
        val profesorIncorporacionError = getProfesoresDefault()[0].copy(fechaIncorporacion = LocalDate.parse("2050-01-01"))
        every { repo.save(getProfesoresDefault()[0]) } returns getProfesoresDefault()[0]
        assertAll(
            { assertEquals(controller.save(getProfesoresDefault()[0]).get(), getProfesoresDefault()[0]) },
            { assertTrue { controller.save(profesorIdError).getError() is ProfesorError.ProfesorIdError } },
            { assertTrue { controller.save(profesorNombreError).getError() is ProfesorError.ProfesorNombreError } },
            { assertTrue { controller.save(profesorIncorporacionError).getError() is ProfesorError.ProfesorFechaIncorporacionError } }
        )
        verify { repo.save(getProfesoresDefault()[0]) }
    }

    @Test
    fun saveAllTest(){
        val profesoresIdError = listOf(
            getProfesoresDefault()[0].copy(id = -1),
            getProfesoresDefault()[1]
        )
        val profesoresNombreError = listOf(
            getProfesoresDefault()[0],
            getProfesoresDefault()[1].copy(nombre = "")
        )
        val profesoresIncorporacionError = listOf(
            getProfesoresDefault()[0],
            getProfesoresDefault()[1].copy(fechaIncorporacion = LocalDate.parse("2050-01-01"))
        )
        every { repo.saveAll(getProfesoresDefault()) } returns Unit
        assertAll(
            { assertTrue { controller.saveAll(getProfesoresDefault()).get() != null } },
            { assertTrue { controller.saveAll(profesoresIdError).getError() is ProfesorError.ProfesorIdError } },
            { assertTrue { controller.saveAll(profesoresNombreError).getError() is ProfesorError.ProfesorNombreError } },
            { assertTrue { controller.saveAll(profesoresIncorporacionError).getError() is ProfesorError.ProfesorFechaIncorporacionError } }
        )
        verify { repo.saveAll(getProfesoresDefault()) }
    }

    @Test
    fun deleteByIdTest(){
        every { repo.deleteById(0) } returns true
        every { repo.deleteById(5) } returns false
        assertAll(
            { assertTrue { controller.deleteById(0).get() != null } },
            { assertTrue { controller.deleteById(5).getError() is ProfesorError.ProfesorNoEncontradoError } }
        )
        verify { repo.deleteById(0) }
        verify { repo.deleteById(5) }
    }

    @Test
    fun deleteTest(){
        every { repo.delete(getProfesoresDefault()[0]) } returns true
        every { repo.delete(getProfesoresDefault()[1]) } returns false
        assertAll(
            { assertTrue { controller.delete(getProfesoresDefault()[0]).get() != null } },
            { assertTrue { controller.delete(getProfesoresDefault()[1]).getError() is ProfesorError.ProfesorNoEncontradoError } }
        )
        verify { repo.delete(getProfesoresDefault()[0]) }
        verify { repo.delete(getProfesoresDefault()[1]) }
    }

    @Test
    fun existsByIdTest(){
        every { repo.existsById(0) } returns true
        every { repo.existsById(5) } returns false
        assertAll(
            { assertTrue { controller.existsById(0).get() != null } },
            { assertTrue { controller.existsById(5).getError() is ProfesorError.ProfesorNoEncontradoError } }
        )
        verify { repo.existsById(0) }
        verify { repo.existsById(5) }
    }
}