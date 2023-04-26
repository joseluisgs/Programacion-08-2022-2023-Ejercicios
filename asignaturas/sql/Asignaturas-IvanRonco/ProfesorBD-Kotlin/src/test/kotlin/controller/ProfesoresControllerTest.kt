package controller

import exceptions.ProfesorException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.Profesor
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import repositorio.profesores.ProfesoresRepositoryBase
import service.storage.profesores.ProfesorStorageService
import java.util.*

internal class ProfesoresControllerTest {

    @MockK
    lateinit var repository : ProfesoresRepositoryBase

    @MockK
    lateinit var storage : ProfesorStorageService

    @InjectMockKs
    lateinit var controller : ProfesoresController

    init {
        MockKAnnotations.init(this)
    }

    val uuid1 = "bf169610-5c15-4c99-bcb0-c52c35ddc724"
    val uuid2 = "62d0cd6f-04db-46e5-993c-49a3af2707c1"

    val profesores get() = listOf<Profesor>(
        Profesor(id = 1, uuid = UUID.fromString(uuid1), nombre = "Romeo", experiencia = 12),
        Profesor(id = 2, uuid = UUID.fromString(uuid2), nombre = "Cajal", experiencia = 3)
    )

    @Test
    fun findByExperiencia() {
        every { repository.findByExperiencia(12) } returns profesores.filter { it.experiencia == 12 }

        val resultList = controller.findByExperiencia(12)
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(profesores[0], resultList[0]) }
        )
    }

    @Test
    fun findByUuid() {
        every { repository.findByUuid(UUID.fromString(uuid1)) } returns profesores[0]

        val result = controller.findByUuid(UUID.fromString(uuid1))
        assertEquals(profesores[0], result)
    }

    @Test
    fun findByUuidButNotFound() {
        every { repository.findByUuid(UUID.fromString(uuid1)) } returns null

        val message = assertThrows<ProfesorException.ProfesorNotFoundException>{ controller.findByUuid(UUID.fromString(uuid1)) }.message
        assertEquals(
            "No se encontró al ingrediente de con el identificador: $uuid1",
            message
        )
    }

    @Test
    fun findByName() {
        every { repository.findByName("Ro") } returns listOf(profesores[0])

        val resultList = controller.findByName("Ro")
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(profesores[0], resultList[0]) }
        )
    }

    @Test
    fun findAll() {
        every { repository.findAll() } returns profesores

        val resultList = controller.findAll()
        assertAll(
            { assertEquals(2, resultList.size) },
            { assertEquals(profesores[0], resultList[0]) },
            { assertEquals(profesores[1], resultList[1]) }
        )
    }

    @Test
    fun findById() {
        every { repository.findById(1) } returns profesores[0]

        val result = controller.findById(1)
        assertEquals(profesores[0], result)
    }

    @Test
    fun findByIdButNotFound() {
        every { repository.findById(1) } returns null

        val message = assertThrows<ProfesorException.ProfesorNotFoundException>{ controller.findById(1) }.message
        assertEquals(
            "No se encontró al ingrediente de con el identificador: 1",
            message
        )
    }

    @Test
    fun saveComoCreate() {
        val ingrediente = profesores[0].copy(id = 3)
        every { repository.save(ingrediente) } returns ingrediente

        val result = controller.save(ingrediente)
        assertEquals(ingrediente, result )
    }

    @Test
    fun saveComoUpdate() {
        val ingrediente = profesores[0].copy(nombre = "Robin")
        every { repository.save(ingrediente) } returns ingrediente

        val result = controller.save(ingrediente)
        assertEquals(ingrediente, result )
    }

    @Test
    fun delete() {
        every { repository.delete(profesores[0]) } returns true

        val result = controller.delete(profesores[0])
        assertTrue(result)
    }

    @Test
    fun deleteButNotFound() {
        every { repository.delete(profesores[0]) } returns false

        val message = assertThrows<ProfesorException.ProfesorNotFoundException>{ controller.delete(profesores[0]) }.message
        assertEquals(
            "No se encontró al ingrediente de con el identificador: 1",
            message
        )
    }

    @Test
    fun deleteById() {
        every { repository.deleteById(1) } returns true

        val result = controller.deleteById(1)
        assertTrue(result)
    }

    @Test
    fun deleteByIdButNotFound() {
        every { repository.deleteById(1) } returns false

        val message = assertThrows<ProfesorException.ProfesorNotFoundException>{ controller.deleteById(1) }.message
        assertEquals(
            "No se encontró al ingrediente de con el identificador: 1",
            message
        )
    }
}