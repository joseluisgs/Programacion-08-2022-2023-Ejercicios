package controllers

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import controllers.modulo.ModuloController
import controllers.profesor.ProfesorController
import errors.ModuloError
import errors.ProfesorError
import factories.ModuloFactory.getModulosDefault
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
import repositories.modulo.ModuloRepositoryMap
import repositories.profesor.ProfesorRepositoryMap
import services.storage.modulo.ModuloFileCsv
import services.storage.profesor.ProfesorFileCsv
import java.time.LocalDate
import java.util.UUID
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class ModuloControllerTest {
    @MockK
    private lateinit var storage: ModuloFileCsv
    @MockK
    private lateinit var repo: ModuloRepositoryMap

    @InjectMockKs
    private lateinit var controller: ModuloController

    private val UUIDExiste = UUID.fromString("ab169d2f-8f46-4bf3-b327-49f372f3e55d")
    private val UUIDNoExiste = UUID.fromString("d520fa71-467e-454a-b2e0-d895e4d8b75c")

    @Test
    fun findAllTest(){
        every { repo.findAll() } returns getModulosDefault()
        assertEquals(
            getProfesoresDefault().size,
            controller.findAll().toList().size
        )
        verify { repo.findAll() }
    }

    @Test
    fun findById(){
        every { repo.findById(UUIDExiste) } returns getModulosDefault()[0]
        every { repo.findById(UUIDNoExiste) } returns null
        assertAll(
            { assertEquals(controller.findById(UUIDExiste).get(), getModulosDefault()[0]) },
            { assertTrue { controller.findById(UUIDNoExiste).getError() is ModuloError.ModuloNoEncontradoError } }
        )
        verify { repo.findById(UUIDExiste) }
        verify { repo.findById(UUIDNoExiste) }
    }

    @Test
    fun saveTest(){
        val profesorIdError = getModulosDefault()[0].copy(nombre = "")
        val profesorNombreError = getModulosDefault()[0].copy(curso = 3)
        every { repo.save(getModulosDefault()[0]) } returns getModulosDefault()[0]
        assertAll(
            { assertEquals(controller.save(getModulosDefault()[0]).get(), getModulosDefault()[0]) },
            { assertTrue { controller.save(profesorIdError).getError() is ModuloError.ModuloNombreError } },
            { assertTrue { controller.save(profesorNombreError).getError() is ModuloError.ModuloCursoError } },
        )
        verify { repo.save(getModulosDefault()[0]) }
    }

    @Test
    fun saveAllTest(){
        val moduloNombreError = listOf(
            getModulosDefault()[0].copy(nombre = ""),
            getModulosDefault()[1]
        )
        val moduloCursoError = listOf(
            getModulosDefault()[0],
            getModulosDefault()[1].copy(curso = 3)
        )
        every { repo.saveAll(getModulosDefault()) } returns Unit
        assertAll(
            { assertTrue { controller.saveAll(getModulosDefault()).get() != null } },
            { assertTrue { controller.saveAll(moduloNombreError).getError() is ModuloError.ModuloNombreError } },
            { assertTrue { controller.saveAll(moduloCursoError).getError() is ModuloError.ModuloCursoError } },
        )
        verify { repo.saveAll(getModulosDefault()) }
    }

    @Test
    fun deleteByIdTest(){
        every { repo.deleteById(UUIDExiste) } returns true
        every { repo.deleteById(UUIDNoExiste) } returns false
        assertAll(
            { assertTrue { controller.deleteById(UUIDExiste).get() != null } },
            { assertTrue { controller.deleteById(UUIDNoExiste).getError() is ModuloError.ModuloNoEncontradoError } }
        )
        verify { repo.deleteById(UUIDExiste) }
        verify { repo.deleteById(UUIDNoExiste) }
    }

    @Test
    fun deleteTest(){
        every { repo.delete(getModulosDefault()[0]) } returns true
        every { repo.delete(getModulosDefault()[1]) } returns false
        assertAll(
            { assertTrue { controller.delete(getModulosDefault()[0]).get() != null } },
            { assertTrue { controller.delete(getModulosDefault()[1]).getError() is ModuloError.ModuloNoEncontradoError } }
        )
        verify { repo.delete(getModulosDefault()[0]) }
        verify { repo.delete(getModulosDefault()[1]) }
    }

    @Test
    fun existsByIdTest(){
        every { repo.existsById(UUIDExiste) } returns true
        every { repo.existsById(UUIDNoExiste) } returns false
        assertAll(
            { assertTrue { controller.existsById(UUIDExiste).get() != null } },
            { assertTrue { controller.existsById(UUIDNoExiste).getError() is ModuloError.ModuloNoEncontradoError } }
        )
        verify { repo.existsById(UUIDExiste) }
        verify { repo.existsById(UUIDNoExiste) }
    }
}