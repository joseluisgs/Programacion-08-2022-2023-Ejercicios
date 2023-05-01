package repositories

import config.ConfigApp
import models.Burguer
import models.Ingrediente
import models.LineaBurguer
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import services.database.DataBaseManagerGeneralDDL
import java.nio.file.Files
import java.time.LocalDateTime
import java.util.*
import kotlin.io.path.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BurguerRepositoryTest {

    private val repository = BurguerRepository()

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
        repeat(3){
            val lineasBurguer = mutableListOf<LineaBurguer>()
            val newUUIDburguer: UUID = UUID.randomUUID()

            val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
            lineasBurguer.add(LineaBurguer(0, newUUIDburguer,ingredeint.id, 2,ingredeint.price))

            repository.saveItem(Burguer(newUUIDburguer, "abcd",1, lineasBurguer))
        }
    }

    @Test
    fun getAllTest() {
        val items = repository.getAll()
        assertEquals(3, items.count())
    }

    @Test
    fun getByIdTest() {

        val uuidToSearch = repository.getAll()[0].uuid
        val itemToSearch = repository.getAll()[0]
        val itemSearched = repository.getById(itemToSearch)

        assertAll(
            { assertNotNull(itemSearched) },
            { assertEquals(uuidToSearch, itemSearched!!.uuid) },
            { assertEquals(repository.getAll()[0].name, itemSearched!!.name) },
        )
    }

    @Test
    fun saveItemTest() {
        // Preparo el item a guardar
        val lineasBurguer = mutableListOf<LineaBurguer>()
        val uuidToSearch: UUID = UUID.randomUUID()

        val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
        lineasBurguer.add(LineaBurguer(0, uuidToSearch,ingredeint.id, 2,ingredeint.price))

        var updateItem = repository.saveItem(Burguer(uuidToSearch, "cccc",1, lineasBurguer))

        val items = repository.getAll()

        assertAll(
            { assertNotNull(updateItem) },
            { assertEquals(uuidToSearch, updateItem.uuid) },
            { assertEquals("cccc", updateItem.name) },
            { assertEquals(4, items.count()) }
        )

        // Actualizamos el item solo el nombre
        updateItem =repository.saveItem(Burguer(uuidToSearch, "dddd",1, lineasBurguer))

        assertAll(
            { assertNotNull(updateItem) },
            { assertEquals(uuidToSearch, updateItem.uuid) },
            { assertEquals("dddd", updateItem.name) },
            { assertEquals(4, items.count()) }
        )
    }

    @Test
    fun deleteItemTest() {
        // Caso borrado correcto, ya que existe el item
        assertTrue(repository.deleteItem(repository.getAll()[0]))

        // Caso borrado incorrecto, no existe el item
        val lineasBurguer = mutableListOf<LineaBurguer>()
        val uuidToSearch: UUID = UUID.randomUUID()

        val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
        lineasBurguer.add(LineaBurguer(0, uuidToSearch,ingredeint.id, 2,ingredeint.price))

        val itemToDelete2 = Burguer(uuidToSearch, "cccc",1, lineasBurguer)
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
        assertTrue(repository.existItem(repository.getAll()[0]))


        // Caso existe incorrecto
        val lineasBurguer = mutableListOf<LineaBurguer>()
        val uuidToSearch: UUID = UUID.randomUUID()

        val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
        lineasBurguer.add(LineaBurguer(0, uuidToSearch,ingredeint.id, 2,ingredeint.price))

        val itemNotExist = Burguer(uuidToSearch, "cccc",1, lineasBurguer)
        assertFalse(repository.deleteItem(itemNotExist))
    }
}