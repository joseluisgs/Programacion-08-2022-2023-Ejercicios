package controllers

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.Conductor
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import repositories.conductores.ConductorRepository
import services.conductor.ConductorStorageImp
import java.time.LocalDate
import java.util.*

internal class ConductorControllerTest {

    @MockK
    private lateinit var repository : ConductorRepository
    @MockK
    private lateinit var  storage: ConductorStorageImp

    @InjectMockKs
    private lateinit var controller : ConductorController


    init {
        MockKAnnotations.init(this)
    }

    private val conductor1 = Conductor(
        uuid = UUID.randomUUID(),
        nombre = "Carlos",
        fechaCarnet = LocalDate.now()
    )

    private val conductor2 = Conductor(
        uuid = UUID.randomUUID(),
        nombre = "Juan",
        fechaCarnet = LocalDate.now()
    )
    private val conductor3 = Conductor(
        uuid = UUID.randomUUID(),
        nombre = "Carlos",
        fechaCarnet = LocalDate.now()
    )


    private val conductores = listOf(
        conductor1, conductor2, conductor3
    )

    @Test
    fun clearTables() {
        every { repository.clearTables() } returns true

        val res = controller.clearTables()

        assertTrue(res)

    }

    @Test
    fun deleteById() {
        every { repository.deleteFromDatabaseById(conductor1.uuid.toString()) } returns true
        val res = controller.deleteById(conductor1.uuid.toString())

        assertEquals(repository.deleteFromDatabaseById(conductor1.uuid.toString()), res)
    }

    @Test
    fun save() {
        every { repository.saveIntoDataBase(conductor1) } returns conductor1
        val res = controller.save(conductor1)

        assertEquals(repository.saveIntoDataBase(conductor1), res)
    }

    @Test
    fun saveIntoJson() {
        every { storage.saveIntoJson(conductores) } returns conductores
        val res = controller.saveIntoJson(conductores)

        assertEquals(storage.saveIntoJson(conductores), res)
    }

    @Test
    fun loadFromJson() {
        every { storage.loadDatafromJson() } returns conductores
        val res = controller.loadFromJson()

        assertEquals(storage.loadDatafromJson(), res)
    }

    @Test
    fun loadFromCSV() {
        every { storage.loadDataFromCsv() } returns conductores
        val res = controller.loadFromCSV()

        assertEquals(storage.loadDataFromCsv(), res)
    }

}