package services.storages

import config.ConfigApp
import models.Conductor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class ConductorStorageCsvTest {

    private val typeExtension = "csv"

    private val storage = ConductorStorage()

    private val dataToTest = mutableListOf<Conductor>()

    init {
        dataToTest.add(Conductor(UUID.randomUUID(), "aaaa"))
        dataToTest.add(Conductor(UUID.randomUUID(), "bbbb"))
        dataToTest.add(Conductor(UUID.randomUUID(), "cccc"))
    }

    @Test
    fun writeFileToCsvTest() {
        storage.writeFileToCsv(dataToTest)

        val outputFile = File(ConfigApp.getPathDataOutput(typeExtension))
        assert(outputFile.exists())
    }

    @Test
    fun readFileOfCsvTest() {
        // Creamos el fichero para poderlo leer
        storage.writeFileToCsv(dataToTest)

        val result = storage.readFileOfCsv()
        assertEquals(dataToTest.size, result.size)
    }

}