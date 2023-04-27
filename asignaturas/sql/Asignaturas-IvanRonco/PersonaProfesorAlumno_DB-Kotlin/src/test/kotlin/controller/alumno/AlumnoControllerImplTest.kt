package controller.alumno

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.Alumno
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import repository.alumnos.AlumnoRepositoryImpl
import service.storage.alumnos.AlumnoStorageService

internal class AlumnoControllerImplTest {

    @MockK
    private lateinit var repositoryImpl: AlumnoRepositoryImpl

    @MockK
    private lateinit var storage: AlumnoStorageService

    @InjectMockKs
    private lateinit var controller: AlumnoControllerImpl

    init {
        MockKAnnotations.init(this)
    }

    val alumnos = listOf(
        Alumno(1L, "Juan", 23), Alumno(2L, "Carmen", 34)
    )

    @Test
    fun findByEdad() {
        every { repositoryImpl.findByEdad(23) } returns listOf(alumnos[0])

        val alumnosRes = controller.findByEdad(23)
        assertAll(
            { assertTrue { alumnosRes.size == 1 } },
            { assertEquals(alumnosRes[0], alumnos[0])}
        )
    }

    @Test
    fun findAll() {
        every { repositoryImpl.findAll() } returns alumnos

        val alumnosRes = controller.findAll()
        assertAll(
            { assertTrue { alumnosRes.size == 2 } },
            { assertEquals(alumnosRes[0], alumnos[0]) },
            { assertEquals(alumnosRes[1], alumnos[1]) }
        )
    }

    @Test
    fun findById() {
        every { repositoryImpl.findById(1L) } returns alumnos[0]

        assertEquals(alumnos[0], controller.findById(1L).get())
    }

    @Test
    fun findByIdButNotFound() {
        every { repositoryImpl.findById(1L) } returns null

        assertEquals("La persona con el identificador: 1, no ha sido encontrada.", controller.findById(1L).getError()!!.message)
    }

    @Test
    fun save() {
        val alumno = Alumno(nombre = "Gabriel", edad = 45)

        every { repositoryImpl.save(alumno) } returns alumno

        assertEquals(alumno, controller.save(alumno).get())
    }

    @Test
    fun delete() {
        every { repositoryImpl.delete(alumnos[0]) } returns true

        assertTrue { controller.delete(alumnos[0]).get()!! }
    }

    @Test
    fun deleteButNotFound() {
        every { repositoryImpl.delete(alumnos[0]) } returns false

        assertEquals("La persona con el identificador: 1, no ha sido encontrada.", controller.delete(alumnos[0]).getError()!!.message)
    }

    @Test
    fun deleteById() {
        every { repositoryImpl.deleteById(alumnos[0].id) } returns true

        assertTrue { controller.deleteById(alumnos[0].id).get()!! }
    }

    @Test
    fun deleteByIdButNotFound() {
        every { repositoryImpl.deleteById(alumnos[0].id) } returns false

        assertEquals("La persona con el identificador: 1, no ha sido encontrada.", controller.deleteById(alumnos[0].id).getError()!!.message)
    }
}