package services.storages

import config.ConfigApp
import models.Conductor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class ConductorStorageXmlTest {

    private val typeExtension = "xml"

    private val storage = ConductorStorage()

    private val dataToTest = mutableListOf<Conductor>()

    init {
        dataToTest.add(Conductor(UUID.randomUUID(), "aaaa"))
        dataToTest.add(Conductor(UUID.randomUUID(), "bbbb"))
        dataToTest.add(Conductor(UUID.randomUUID(), "cccc"))
    }

    @Test
    fun writeFileToXmlTest() {
        storage.writeFileToXml(dataToTest)

        val outputFile = File(ConfigApp.getPathDataOutput(typeExtension))
        assert(outputFile.exists())
    }

    @Test
    fun readFileOfXmlTest() {
        // Creamos el fichero para poderlo leer
        storage.writeFileToXml(dataToTest)

        val result = storage.readFileOfXml()
        Assertions.assertEquals(dataToTest.size, result.size)
    }

}