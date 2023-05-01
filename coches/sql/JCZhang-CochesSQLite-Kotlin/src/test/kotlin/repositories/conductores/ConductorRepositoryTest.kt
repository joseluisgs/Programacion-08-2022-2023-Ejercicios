package repositories.conductores


import models.Conductor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import services.database.CochesDataBaseService
import java.time.LocalDate
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ConductorRepositoryTest {

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

    @BeforeAll
    fun iniDatabase(){
        CochesDataBaseService
    }

    @BeforeEach
    fun setUp() {
        ConductorRepository.clearTables()
        conductores.forEach { ConductorRepository.saveIntoDataBase(it) }
    }

    @Test
    fun saveIntoDataBase() {
        val res = ConductorRepository.saveIntoDataBase(conductor1)
        assertEquals(conductor1, res)
    }

    @Test
    fun clearTables() {
        val res = ConductorRepository.clearTables()
        assertTrue(res)
    }

    @Test
    fun deleteFromDatabaseById() {
        val res = ConductorRepository.deleteFromDatabaseById(conductor1.uuid.toString())
        assertTrue(res)
    }
}