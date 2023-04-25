package repositories.profesor

import factories.ProfesorFactory.getProfesoresDefault
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import java.util.*
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ProfesorRepositoryGenericTest {
    private lateinit var repository: ProfesorRepository
    abstract fun getRepository(): ProfesorRepository

    @BeforeEach
    fun setUp() {
        repository = getRepository()
        repository.deleteAll()
        repository.saveAll(getProfesoresDefault())
    }

    @Test
    fun findAllTest(){
        val profesores = repository.findAll()
        println()
        assertAll(
            { assertEquals(getProfesoresDefault().size, repository.findAll().toList().size) },
            { assertEquals(getProfesoresDefault()[0], profesores.toList()[0]) },
            { assertEquals(getProfesoresDefault()[1], profesores.toList()[1]) },
            { assertEquals(getProfesoresDefault()[2], profesores.toList()[2]) },
        )
    }

    @Test
    fun findByIdTest(){
        val result = repository.findById(1)
        val resultFail = repository.findById(4)
        val paco = repository.findAll()
        println()
        assertAll(
            { assertEquals(getProfesoresDefault()[0], result) },
            { assertEquals(null, resultFail) },
        )
    }

    @Test
    fun createTest(){
        val profesor = getProfesoresDefault()[2].copy(id = 4, nombre = "NEW")
        val paco = repository.findAll()
        println()
        repository.save(profesor)
        assertAll(
            { assertEquals(4, repository.findAll().toList().size) },
            { assertEquals(profesor, repository.findById(4)) },
        )
    }

    @Test
    fun updateTest(){
        val profesor = getProfesoresDefault()[2].copy(nombre = "NEW")
        repository.save(profesor)
        val paco = repository.findAll()
        println()
        assertAll(
            { assertEquals(3, repository.findAll().toList().size) },
            { assertEquals(profesor, repository.findById(profesor.id)) },
        )
    }

    @Test
    fun deleteByIdTest(){
        repository.deleteById(1)
        val paco = repository.findAll()
        println()
        assertAll(
            { assertEquals(2, repository.findAll().toList().size) },
            { assertEquals(null, repository.findById(1)) },
        )
    }

    @Test
    fun deleteAllTest(){
        repository.deleteAll()
        assertAll(
            { assertEquals(0, repository.findAll().toList().size) },
        )
    }
}