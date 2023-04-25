package repositories.modulo

import factories.ModuloFactory.getModulosDefault
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import java.util.*
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ModuloRepositoryGenericTest {
    private lateinit var repository: ModuloRepository
    abstract fun getRepository(): ModuloRepository

    @BeforeEach
    fun setUp() {
        repository = getRepository()
        repository.deleteAll()
        repository.saveAll(getModulosDefault())
    }

    @Test
    fun findAllTest(){
        val modulos = repository.findAll()
        assertAll(
            { assertEquals(getModulosDefault().size, repository.findAll().toList().size) },
            { assertEquals(getModulosDefault()[0], modulos.toList()[0]) },
            { assertEquals(getModulosDefault()[1], modulos.toList()[1]) },
            { assertEquals(getModulosDefault()[2], modulos.toList()[2]) },
        )
    }

    @Test
    fun findByIdTest(){
        val result = repository.findById(UUID.fromString("ab169d2f-8f46-4bf3-b327-49f372f3e55d"))
        val resultFail = repository.findById(UUID.fromString("a6f13e8c-2d97-4e37-ac98-2df65128241b"))
        assertAll(
            { assertEquals(getModulosDefault()[0], result) },
            { assertEquals(null, resultFail) },
        )
    }

    @Test
    fun createTest(){
        val modulo = getModulosDefault()[2].copy(uuid = UUID.randomUUID(), nombre = "NEW")
        repository.save(modulo)
        assertAll(
            { assertEquals(4, repository.findAll().toList().size) },
            { assertEquals(modulo, repository.findById(modulo.uuid)) },
        )
    }

    @Test
    fun updateTest(){
        val modulo = getModulosDefault()[2].copy(nombre = "NEW")
        repository.save(modulo)
        assertAll(
            { assertEquals(3, repository.findAll().toList().size) },
            { assertEquals(modulo, repository.findById(modulo.uuid)) },
        )
    }

    @Test
    fun deleteByIdTest(){
        repository.deleteById(UUID.fromString("ab169d2f-8f46-4bf3-b327-49f372f3e55d"))
        assertAll(
            { assertEquals(2, repository.findAll().toList().size) },
            { assertEquals(null, repository.findById(UUID.fromString("ab169d2f-8f46-4bf3-b327-49f372f3e55d"))) },
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