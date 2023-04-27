package repositorio.ingredientes

import models.Ingrediente
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import java.nio.file.Files
import java.util.*
import kotlin.io.path.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class IngredienteRepositorySQLiteTest {

    val repository = IngredienteRepositorySQLite

    val uuid1 = "bf169610-5c15-4c99-bcb0-c52c35ddc724"
    val uuid2 = "62d0cd6f-04db-46e5-993c-49a3af2707c1"

    val ingredientes = listOf<Ingrediente>(
        Ingrediente(uuid = UUID.fromString(uuid1), nombre = "Carne", precio = 3.46, cantidad = 14),
        Ingrediente(uuid = UUID.fromString(uuid2), nombre = "Cebolla", precio = 1.56, cantidad = -35)
    )

    @AfterAll
    fun tierDownAll(){
        Files.deleteIfExists(Path("ingrediente_test.db"))
    }

    @BeforeEach
    fun setUp(){
        repository.deleteAll()

        ingredientes.forEach {
            repository.save(it)
        }
    }

    @Test
    fun findByDisponible() {
        val resultList = repository.findByDisponible(true)
        assertAll(
            { assertEquals(1, resultList.size) },
            //Por como definí el constructor por defecto de Ingrediente, si la cantidad es menor o igual que cero, disponible será false
            { assertEquals(ingredientes[0], resultList[0]) }
        )
    }

    @Test
    fun findByUuid() {
        val result = repository.findByUuid(UUID.fromString(uuid1))
        assertEquals(ingredientes[0], result)
    }

    @Test
    fun findByName() {
        val resultList = repository.findByName("Car")
        assertAll(
            { assertEquals(1, resultList.size) },
            //Debido a como creé esta función, se que "Car", me sacare el ingrediente que se llama: "Carne". Nota: Use un contains, y lo puse todo a lowwercase
            { assertEquals(ingredientes[0], resultList[0]) }
        )
    }

    @Test
    fun findAll() {
        val resultList = repository.findAll()
        assertAll(
            { assertEquals(2, resultList.size) },
            { assertEquals(ingredientes[0], resultList[0]) },
            { assertEquals(ingredientes[1], resultList[1]) }
        )
    }

    @Test
    fun findById() {
        val result = repository.findById(repository.findAll().maxOf { it.id }-1)
        assertEquals(ingredientes[0], result)
    }

    @Test
    fun saveComoCreate() {
        val uuid = "78554f5b-d08e-49f2-96e8-1bf0f2494040"
        val ingrediente = Ingrediente(id = repository.findAll().maxOf { it.id }+1, uuid = UUID.fromString(uuid), nombre = "Pan", precio = 8.64, cantidad = 514)
        val result = repository.save(ingrediente)
        assertAll(
            { assertEquals(3, repository.findAll().size) },
            { assertEquals(ingrediente, result) }
        )
    }

    @Test
    fun saveComoUpdate() {
        val ingrediente = repository.findAll()[0].copy(nombre = "Pan")
        val result = repository.save(ingrediente)
        assertAll(
            { assertEquals(2, repository.findAll().size) },
            { assertEquals(ingrediente, result) }
        )
    }

    @Test
    fun delete() {
        // Aunque parezca innecesario, la parte del .copy() sirve para igualar al id correcto
        val result = repository.delete(ingredientes[0].copy(id = repository.findAll().maxOf { it.id }))
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