package repositories.Coches

import controllers.CocheController
import models.Coche
import models.TipoMotor
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import services.database.CochesDataBaseService


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CocheRepositoryTest {

    private val vehiculo1 = Coche(0, "Ferrari", "A234", 15000.00, TipoMotor.GASOLINA)
    private val vehiculo2 = Coche(0, "Citroen", "F344", 24000.00, TipoMotor.ELECTRICO)
    private val vehiculo3 = Coche(0, "Volvo", "KJ89", 9000.00, TipoMotor.HIBRIDO)

    val vehiculos = listOf(
        vehiculo1, vehiculo2, vehiculo3
    )

    @BeforeAll
    fun initDatabase(){
        CochesDataBaseService
    }

    @BeforeEach
    fun setUp() {
        CocheRepository.clearTables()
        vehiculos.forEach { CocheRepository.saveIntoDataBase(it) }
    }

    @Test
    fun saveIntoDataBase() {
        val res = CocheRepository.saveIntoDataBase(vehiculo1)
        assertEquals(vehiculo1, res)
    }

    @Test
    fun clearTables() {
        val res = CocheRepository.clearTables()
        assertTrue(res)
    }

    @Test
    fun deleteFromDatabaseById() {
        val res = CocheRepository.deleteFromDatabaseById(vehiculo1.id)
        assertTrue(res)
    }
}