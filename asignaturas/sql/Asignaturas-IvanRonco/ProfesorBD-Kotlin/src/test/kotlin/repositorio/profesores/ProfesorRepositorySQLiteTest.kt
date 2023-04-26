package repositorio.profesores

import models.Profesor
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.*

internal class ProfesorRepositorySQLiteTest {

    val repository = ProfesoresRepositorySQLite

    val uuid1 = "bf169610-5c15-4c99-bcb0-c52c35ddc724"
    val uuid2 = "62d0cd6f-04db-46e5-993c-49a3af2707c1"

    val profesores = listOf<Profesor>(
        Profesor(uuid = UUID.fromString(uuid1), nombre = "Romeo", experiencia = 12),
        Profesor(uuid = UUID.fromString(uuid2), nombre = "Cajal", experiencia = 3)
    )

    @BeforeEach
    fun setUp(){
        repository.deleteAll()

        profesores.forEach {
            repository.save(it)
        }
    }

    @Test
    fun findByExperiencia() {
        val resultList = repository.findByExperiencia(12)
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(profesores[0], resultList[0]) }
        )
    }

    @Test
    fun findByUuid() {
        val result = repository.findByUuid(UUID.fromString(uuid1))
        assertEquals(profesores[0], result)
    }

    @Test
    fun findByName() {
        val resultList = repository.findByName("Ro")
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(profesores[0], resultList[0]) }
        )
    }

    @Test
    fun findAll() {
        val resultList = repository.findAll()
        assertAll(
            { assertEquals(2, resultList.size) },
            { assertEquals(profesores[0], resultList[0]) },
            { assertEquals(profesores[1], resultList[1]) }
        )
    }

    @Test
    fun findById() {
        val result = repository.findById(repository.findAll().maxOf { it.id }-1)
        assertEquals(profesores[0], result)
    }

    @Test
    fun saveComoCreate() {
        val uuid = "78554f5b-d08e-49f2-96e8-1bf0f2494040"
        val profesor = Profesor(id = repository.findAll().maxOf { it.id }+1, uuid = UUID.fromString(uuid), nombre = "Pan", experiencia = 25)
        val result = repository.save(profesor)
        assertAll(
            { assertEquals(3, repository.findAll().size) },
            { assertEquals(profesor, result) }
        )
    }

    @Test
    fun saveComoUpdate() {
        val profesor = repository.findAll()[0].copy(nombre = "Gabriel")
        val result = repository.save(profesor)
        assertAll(
            { assertEquals(2, repository.findAll().size) },
            { assertEquals(profesor, result) }
        )
    }

    @Test
    fun delete() {
        val result = repository.delete(profesores[0].copy(id = repository.findAll().maxOf { it.id }))
        assertAll(
            { assertTrue(result) },
            { assertEquals(1, repository.findAll().size) }
        )
    }

    @Test
    fun deleteById() {
        val result = repository.deleteById(repository.findAll().maxOf { it.id })
        assertAll(
            { assertTrue(result) },
            { assertEquals(1, repository.findAll().size) }
        )
    }

    @Test
    fun deleteAll() {
        repository.deleteAll()
        assertEquals(0, repository.findAll().size)
    }
}