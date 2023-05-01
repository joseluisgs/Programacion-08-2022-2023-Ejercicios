package controllers

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import errors.BurguerError
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Burguer
import models.Ingrediente
import models.LineaBurguer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import repositories.BurguerRepository
import services.storages.BurguerStorageCsv
import services.storages.BurguerStorageJson
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class BurguerControllerTest {

    @MockK
    private lateinit var repository: BurguerRepository

    @MockK
    private lateinit var storageCsv: BurguerStorageCsv

    @MockK
    private lateinit var storageJson: BurguerStorageJson

    @InjectMockKs
    private lateinit var controller: BurguerController

    private val dataToTest = mutableListOf<Burguer>()

    init {
        repeat(3){
            val lineasBurguer = mutableListOf<LineaBurguer>()
            val newUUIDburguer: UUID = UUID.randomUUID()

            val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
            lineasBurguer.add(LineaBurguer(0, newUUIDburguer,ingredeint.id, 2,ingredeint.price))

            dataToTest.add(Burguer(newUUIDburguer, "abcd",1, lineasBurguer))
        }
    }

    @Test
    fun getAllTest() {
        every { repository.getAll() } returns dataToTest

        val result = controller.getAll()

        assertAll(
            { assertNotNull(result) },
            { assertEquals(dataToTest.size, result.size) },
        )

        verify { repository.getAll() }
    }

    @Test
    fun getByIdTest() {
        every { repository.getById(any()) } returns dataToTest[0]

        val result = controller.getById(dataToTest[0])

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(result.get()) },
            { assertEquals(dataToTest[0], result.get()) },
        )

        verify { repository.getById(any()) }
    }

    @Test
    fun getByIdNotFound() {
        every { repository.getById(any()) } returns null

        val lineasBurguer = mutableListOf<LineaBurguer>()
        val uuidToSearch: UUID = UUID.randomUUID()

        val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
        lineasBurguer.add(LineaBurguer(0, uuidToSearch,ingredeint.id, 2,ingredeint.price))

        val item = Burguer(uuidToSearch, "cccc",1, lineasBurguer)
        val result = controller.getById(item)

        assertAll(
            { assertNotNull(result) },
            { assertTrue(result.get() == null) },
            { assertTrue(result.getError() is BurguerError.NotFound) },
            { assertEquals("No se ha encontrado el item con id ${item.uuid}", result.getError()!!.message) },
        )

        verify { repository.getById(any()) }
    }

    @Test
    fun saveItemTest() {
        every { repository.saveItem(any()) } returns dataToTest[0]

        val result = controller.saveItem(dataToTest[0])

        assertAll(
            { assertNotNull(result) },
            { assertEquals(dataToTest[0], result.get()) },
        )
    }

    @Test
    fun saveItemInvalidName() {

        val lineasBurguer = mutableListOf<LineaBurguer>()
        val uuidToSearch: UUID = UUID.randomUUID()

        val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
        lineasBurguer.add(LineaBurguer(0, uuidToSearch,ingredeint.id, 2,ingredeint.price))

        val item = Burguer(uuidToSearch, "c",1, lineasBurguer)

        val result = controller.saveItem(item)

        assertAll(
            { assertNotNull(result) },
            { assertTrue(result.get() == null) },
            { assertTrue(result.getError() is BurguerError.NameInvalid) },
            { assertEquals("El nombre no puede estar vacío y menor de 3 letras", result.getError()?.message) },
        )
    }

    @Test
    fun deleteItemTest() {
        every { repository.deleteItem(any()) } returns true

        val result = controller.deleteItem(dataToTest[0])

        assertAll(
            { assertNotNull(result) },
            { assertTrue(result.get() == true) },
        )

        verify { repository.deleteItem(any()) }
    }

    @Test
    fun deleteNotFound() {
        every { repository.deleteItem(any()) } returns false

        val lineasBurguer = mutableListOf<LineaBurguer>()
        val uuidToSearch: UUID = UUID.randomUUID()

        val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
        lineasBurguer.add(LineaBurguer(0, uuidToSearch,ingredeint.id, 2,ingredeint.price))

        val item = Burguer(uuidToSearch, "c",1, lineasBurguer)
        val result = controller.deleteItem(item)

        assertAll(
            { assertNotNull(result) },
            { assertTrue(result.get() == null) },
            { assertTrue(result.getError() != null) },
            { assertTrue(result.getError() is BurguerError.NotFound) },
            { assertEquals("No se ha encontrado el item con id ${item.uuid}", result.getError()?.message) },
        )

        verify { repository.deleteItem(any()) }
    }

    @Test
    fun exportDataToFileJsonTest() {
        controller.setFileType(BurguerController.FileType.JSON)

        every { repository.getAll() } returns dataToTest.toList()
        every { storageJson.writeFile(any()) } returns true

        val result = controller.exportFile()
        assertTrue(result)
    }

    @Test
    fun exportDataToFileCsvTest() {
        controller.setFileType(BurguerController.FileType.CSV)

        every { repository.getAll() } returns dataToTest.toList()
        every { storageCsv.writeFile(any()) } returns true

        val result = controller.exportFile()
        assertTrue(result)
    }

    @Test
    fun importDataJsonToMainTest() {
        controller.setFileType(BurguerController.FileType.JSON)

        every { storageJson.readFile() } returns dataToTest

        val result = controller.importFile()
        assertTrue(dataToTest.size == result.size)
    }

    @Test
    fun importDataCsvToMainTest() {
        controller.setFileType(BurguerController.FileType.CSV)

        every { storageCsv.readFile() } returns dataToTest

        val result = controller.importFile()
        assertTrue(dataToTest.size == result.size)
    }

}
