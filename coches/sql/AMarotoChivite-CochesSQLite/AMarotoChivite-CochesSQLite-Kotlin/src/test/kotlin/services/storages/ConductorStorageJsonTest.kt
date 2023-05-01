package services.storages

import config.ConfigApp
import models.Conductor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class ConductorStorageJsonTest {

    private val typeExtension = "json"

    private val storage = ConductorStorage()

    private val dataToTest = mutableListOf<Conductor>()

    init {
        dataToTest.add(Conductor(UUID.randomUUID(), "aaaa"))
        dataToTest.add(Conductor(UUID.randomUUID(), "bbbb"))
        dataToTest.add(Conductor(UUID.randomUUID(), "cccc"))
    }

    @Test
    fun writeFileToJsonTest() {
        storage.writeFileToJson(dataToTest)

        val outputFile = File(ConfigApp.getPathDataOutput(typeExtension))
        assert(outputFile.exists())
    }

    @Test
    fun readFileOfJsonTest() {
        // Creamos el fichero para poderlo leer
        storage.writeFileToJson(dataToTest)

        val result = storage.readFileOfJson()
        Assertions.assertEquals(dataToTest.size, result.size)
    }

}