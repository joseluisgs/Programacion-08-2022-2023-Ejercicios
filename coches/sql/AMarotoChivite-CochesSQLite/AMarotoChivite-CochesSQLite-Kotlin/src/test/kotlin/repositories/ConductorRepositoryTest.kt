package repositories

import config.ConfigApp
import models.Conductor
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import services.database.DataBaseManagerGeneralDDL
import java.nio.file.Files
import java.util.*
import kotlin.io.path.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Order(1)
class ConductorRepositoryTest {

    private val repository = ConductorRepository()

    @BeforeAll
    fun setUpAll() {
        // Borramos la base de datos, para empezar a probar desde cero, si estuviera en memoria no haría falta
        Files.deleteIfExists(Path("test.db"))

        // Cargamos configuración general
        ConfigApp

        // Cargamos la base de datos (creamos las tablas)
        DataBaseManagerGeneralDDL
    }

    @BeforeEach
    fun setUp() {
        // Para cada prueba empezamos con la base de datos vacía
        repository.deleteAll()

        // Preparo mi arsenal de prueba
        repository.saveItem(Conductor(UUID.randomUUID(), "aaaa"))
        repository.saveItem(Conductor(UUID.randomUUID(), "bbbb"))
        repository.saveItem(Conductor(UUID.randomUUID(), "cccc"))
    }

    @Test
    fun getAllTest() {
        val items = repository.getAll()
        assertEquals(3, items.count())
    }

    @Test
    fun getByIdTest() {
        val uuidToSearch = UUID.randomUUID()
        val itemToSearch = repository.saveItem(Conductor(uuidToSearch, "dddd"))
        val itemSearched = repository.getById(itemToSearch)

        assertAll(
            { assertNotNull(itemSearched) },
            { assertEquals(uuidToSearch, itemSearched!!.uuid) },
            { assertEquals("dddd", itemSearched!!.name) },
        )
    }

    @Test
    fun saveItemTest() {
        // Caso de nuevo item
        val uuidToSearch = UUID.randomUUID()
        var updateItem = repository.saveItem(Conductor(uuidToSearch, "eeee"))

        val items = repository.getAll()

        assertAll(
            { assertNotNull(updateItem) },
            { assertEquals(uuidToSearch, updateItem.uuid) },
            { assertEquals("eeee", updateItem.name) },
            { assertEquals(4, items.count()) }
        )

        // Actualizamos el item solo el nombre
        updateItem = repository.saveItem(Conductor(uuidToSearch, "ffff"))

        assertAll(
            { assertNotNull(updateItem) },
            { assertEquals(uuidToSearch, updateItem.uuid) },
            { assertEquals("ffff", updateItem.name) },
            { assertEquals(4, items.count()) }
        )
    }

    @Test
    fun deleteItemTest() {
        // Caso borrado correcto, ya que existe el item
        val itemToDelete = repository.saveItem(Conductor(UUID.randomUUID(), "gggg"))
        assertTrue(repository.deleteItem(itemToDelete))

        // Caso borrado incorrecto, no existe el item
        val itemToDelete2 = Conductor(UUID.randomUUID(), "wwww")
        assertFalse(repository.deleteItem(itemToDelete2))
    }

    @Test
    fun deleteAllTest() {
        val correctDeleted = repository.deleteAll()
        assertAll(
            { assertEquals(true, correctDeleted) },
            { assertEquals(0, repository.getAll().count()) }
        )
    }

    @Test
    fun existItemTest() {
        // Caso existe correcto
        val itemToDelete = repository.saveItem(Conductor(UUID.randomUUID(), "vvvv"))
        assertTrue(repository.existItem(itemToDelete))

        // Caso existe incorrecto
        val itemToDelete2 = Conductor(UUID.randomUUID(), "wwww")
        assertFalse(repository.existItem(itemToDelete2))
    }
}