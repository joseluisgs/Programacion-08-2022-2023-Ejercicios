package repository.alumnos

import models.Alumno
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import service.database.ConfigDatabase
import java.nio.file.Files
import kotlin.io.path.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AlumnoRepositoryImplTest {

    private val repository = AlumnoRepositoryImpl(ConfigDatabase.queries)

    val alumnos = listOf(
        Alumno(1L, "Juan", 23), Alumno(2L, "Carmen", 34)
    )

    @AfterAll
    fun tearAllDown(){
        Files.deleteIfExists(Path("Database_tests.db"))
    }

    @BeforeEach
    fun setUp(){
        repository.deleteAll()

        alumnos.forEach {
            repository.save(it)
        }
    }

    @Test
    fun findByEdad() {
        val alumnosRes = repository.findByEdad(23)
        assertAll(
            { assertTrue { alumnosRes.size == 1 } },
            { assertEquals(alumnosRes[0], alumnos[0])}
        )
    }

    @Test
    fun findAll() {
        val alumnosRes = repository.findAll()
        assertAll(
            { assertTrue { alumnosRes.size == 2 } },
            { assertEquals(alumnosRes[0], alumnos[0]) },
            { assertEquals(alumnosRes[1], alumnos[1]) }
        )
    }

    @Test
    fun findById() {
        assertEquals(alumnos[0], repository.findById(repository.findAll()[0].id))
    }

    @Test
    fun save() {
        val alumno = Alumno(nombre = "Gabriel", edad = 45)
        val alumnoSave = repository.save(alumno)
        val alumnosSave = repository.findAll()
        assertAll(
            { assertEquals(alumno, alumnoSave) },
            { assertTrue { alumnosSave.size == 3 } },
            { assertEquals(alumnosSave[2], alumno) }
        )
    }

    @Test
    fun delete() {
        repository.delete(alumnos[0])
        val alumnosDelete = repository.findAll()
        assertAll(
            { assertTrue { alumnosDelete.size == 1 } },
            { assertEquals(alumnosDelete[0], alumnos[1]) }
        )
    }

    @Test
    fun deleteById() {
        repository.deleteById(repository.findAll()[0].id)
        val alumnosDelete = repository.findAll()
        assertAll(
            { assertTrue { alumnosDelete.size == 1 } },
            { assertEquals(alumnosDelete[0], alumnos[1]) }
        )
    }

    @Test
    fun deleteAll() {
        repository.deleteAll()
        val alumnosDelete = repository.findAll()
        assertTrue { alumnosDelete.size == 0 }
    }
}