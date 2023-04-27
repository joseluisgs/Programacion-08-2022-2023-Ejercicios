package controllers

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.Coche
import models.TipoMotor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import repositories.Coches.CocheRepository
import repositories.base.DataBaseFunctions
import services.coches.CocheStorageImp
import services.database.CochesDataBaseService


internal class CocheControllerTest {

    @MockK
    private lateinit var  repository: CocheRepository
    @MockK
    private lateinit var storage: CocheStorageImp

    @InjectMockKs
    private lateinit var controller: CocheController

    init {
        MockKAnnotations.init(this)
    }
    private val vehiculo1 = Coche(0, "Ferrari", "A234", 15000.00, TipoMotor.GASOLINA)
    private val vehiculo2 = Coche(0, "Citroen", "F344", 24000.00, TipoMotor.ELECTRICO)
    private val vehiculo3 = Coche(0, "Volvo", "KJ89", 9000.00, TipoMotor.HIBRIDO)

    val vehiculos = listOf(
        vehiculo1, vehiculo2, vehiculo3
    )



    @AfterEach
    fun tearDown() {
    }

    @Test
    fun saveInDataBase() {
        every { repository.saveIntoDataBase(vehiculo1) } returns vehiculo1

        val res = controller.saveInDataBase(vehiculo1)
        assertEquals(vehiculo1, res)
    }


    @Test
    fun deleteById() {

        every { repository.deleteFromDatabaseById(vehiculo1.id) } returns true

        val result = controller.deleteById(vehiculo1.id )

        assertTrue(result)
    }

    @Test
    fun clearTables() {
        every { repository.clearTables() } returns true
        val res = controller.clearTables()

        assertTrue(res)
    }


    @Test
    fun saveIntoJson() {
        every { storage.saveIntoJson(vehiculos) } returns vehiculos
        val res = controller.saveIntoJson(vehiculos)

        assertEquals(storage.saveIntoJson(vehiculos), res)
    }

    @Test
    fun loadFromCsv() {
        every { storage.loadDataFromCsv()} returns vehiculos
        val res = controller.loadFromCsv()

        assertEquals(storage.loadDataFromCsv(), res)
    }

    @Test
    fun loadFromJson() {
        every { storage.loadDatafromJson()} returns vehiculos
        val res = controller.loadFromJson()

        assertEquals(storage.loadDatafromJson(), res)
    }
}