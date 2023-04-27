package controllers

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import errors.ConductorError
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Conductor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.ConductorRepository
import services.storages.ConductorStorage
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class ConductorControllerTest {

    @MockK
    private lateinit var repository: ConductorRepository

    @MockK
    private lateinit var storage: ConductorStorage

    @InjectMockKs
    private lateinit var controller: ConductorController

    private val dataToTest = mutableListOf<Conductor>()

    init {
        dataToTest.add(Conductor(UUID.randomUUID(), "aaaa"))
        dataToTest.add(Conductor(UUID.randomUUID(), "bbbb"))
        dataToTest.add(Conductor(UUID.randomUUID(), "cccc"))
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

        val item = Conductor(UUID.randomUUID(), "ffff")
        val result = controller.getById(item)

        assertAll(
            { assertNotNull(result) },
            { assertTrue(result.get() == null) },
            { assertTrue(result.getError() is ConductorError.NotFound) },
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

        val result = controller.saveItem(Conductor(UUID.randomUUID(), "a"))

        assertAll(
            { assertNotNull(result) },
            { assertTrue(result.get() == null) },
            { assertTrue(result.getError() is ConductorError.NameInvalid) },
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

        val item = Conductor(UUID.randomUUID(), "gggg")
        val result = controller.deleteItem(item)

        assertAll(
            { assertNotNull(result) },
            { assertTrue(result.get() == null) },
            { assertTrue(result.getError() != null) },
            { assertTrue(result.getError() is ConductorError.NotFound) },
            { assertEquals("No se ha encontrado el item con id ${item.uuid}", result.getError()?.message) },
        )

        verify { repository.deleteItem(any()) }
    }

    @Test
    fun exportDataToFileJsonTest() {
        every { repository.getAll() } returns dataToTest.toList()
        every { storage.writeFileToJson(any()) } returns true

        val result = controller.exportDataToFile("json")
        assertTrue(result)
    }

    @Test
    fun exportDataToFileCsvTest() {
        every { repository.getAll() } returns dataToTest.toList()
        every { storage.writeFileToCsv(any()) } returns true

        val result = controller.exportDataToFile("csv")
        assertTrue(result)
    }

    @Test
    fun exportDataToFileXmlTest() {
        every { repository.getAll() } returns dataToTest.toList()
        every { storage.writeFileToXml(any()) } returns true

        val result = controller.exportDataToFile("xml")
        assertTrue(result)
    }

    @Test
    fun exportDataToFileFailTest() {
        val typeExtension = "txt"
        val exception = assertThrows<IllegalArgumentException> { controller.exportDataToFile(typeExtension) }
        assertEquals("Tipo de fichero no apto: $typeExtension", exception.message)
    }

    @Test
    fun importDataCsvToDataBaseTest() {
        every { storage.readFileOfCsv() } returns dataToTest

        dataToTest.forEach {
            every { repository.saveItem(it) } returns it
        }

        val result = controller.importDataToDataBase("csv")
        assertTrue(result)
    }

    @Test
    fun importDataJsonToDataBaseTest() {
        every { storage.readFileOfJson() } returns dataToTest

        dataToTest.forEach {
            every { repository.saveItem(it) } returns it
        }

        val result = controller.importDataToDataBase("json")
        assertTrue(result)
    }

    @Test
    fun importDataXmlToDataBaseTest() {
        every { storage.readFileOfXml() } returns dataToTest

        dataToTest.forEach {
            every { repository.saveItem(it) } returns it
        }

        val result = controller.importDataToDataBase("xml")
        assertTrue(result)
    }

    @Test
    fun importDataXmlToMainTest() {
        every { storage.readFileOfXml() } returns dataToTest

        val result = controller.importDataToMain("xml")
        assertTrue(dataToTest.size == result!!.size)
    }

    @Test
    fun importDataJsonToMainTest() {
        every { storage.readFileOfJson() } returns dataToTest

        val result = controller.importDataToMain("json")
        assertTrue(dataToTest.size == result!!.size)
    }

    @Test
    fun importDataCsvToMainTest() {
        every { storage.readFileOfXml() } returns dataToTest

        val result = controller.importDataToMain("csv")
        assertTrue(dataToTest.size == result!!.size)
    }

}
