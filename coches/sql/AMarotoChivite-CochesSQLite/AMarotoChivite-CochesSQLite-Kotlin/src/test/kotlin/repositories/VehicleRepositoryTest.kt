package repositories

import config.ConfigApp
import models.Vehicle
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import services.database.DataBaseManagerGeneralDDL
import java.nio.file.Files
import java.util.*
import kotlin.io.path.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehicleRepositoryTest {

    private val repository = VehicleRepository()

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
        repository.saveItem(Vehicle(UUID.randomUUID(), "aaaa", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID()))
        repository.saveItem(Vehicle(UUID.randomUUID(), "bbbb", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID()))
        repository.saveItem(Vehicle(UUID.randomUUID(), "cccc", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID()))
    }

    @Test
    fun getAllTest() {
        val items = repository.getAll()
        assertEquals(3, items.count())
    }

    @Test
    fun getByIdTest() {
        val uuidToSearch = UUID.randomUUID()
        val itemToSearch =
            repository.saveItem(Vehicle(uuidToSearch, "dddd", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID()))
        val itemSearched = repository.getById(itemToSearch)

        assertAll(
            { assertNotNull(itemSearched) },
            { assertEquals(uuidToSearch, itemSearched!!.uuid) },
            { assertEquals("dddd", itemSearched!!.model) },
        )
    }

    @Test
    fun saveItemTest() {
        // Caso de nuevo item
        val uuidToSearch = UUID.randomUUID()
        var updateItem =
            repository.saveItem(Vehicle(uuidToSearch, "eeee", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID()))

        val items = repository.getAll()

        assertAll(
            { assertNotNull(updateItem) },
            { assertEquals(uuidToSearch, updateItem.uuid) },
            { assertEquals("eeee", updateItem.model) },
            { assertEquals(4, items.count()) }
        )

        // Actualizamos el item solo el nombre
        updateItem = repository.saveItem(Vehicle(uuidToSearch, "ffff", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID()))

        assertAll(
            { assertNotNull(updateItem) },
            { assertEquals(uuidToSearch, updateItem.uuid) },
            { assertEquals("ffff", updateItem.model) },
            { assertEquals(4, items.count()) }
        )
    }

    @Test
    fun deleteItemTest() {
        // Caso borrado correcto, ya que existe el item
        val itemToDelete =
            repository.saveItem(Vehicle(UUID.randomUUID(), "vvvv", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID()))
        assertTrue(repository.deleteItem(itemToDelete))

        // Caso borrado incorrecto, no existe el item
        val itemToDelete2 = Vehicle(UUID.randomUUID(), "wwww", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID())
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
        val itemToDelete =
            repository.saveItem(Vehicle(UUID.randomUUID(), "vvvv", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID()))
        assertTrue(repository.existItem(itemToDelete))

        // Caso existe incorrecto
        val itemToDelete2 = Vehicle(UUID.randomUUID(), "wwww", Vehicle.TypeMotor.HIBRIDO, UUID.randomUUID())
        assertFalse(repository.existItem(itemToDelete2))
    }
}