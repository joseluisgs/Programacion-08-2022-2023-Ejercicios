package services.storages

import config.ConfigApp
import models.Vehicle
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class VehicleStorageXmlTest {

    private val typeExtension = "xml"

    private val storage = VehicleStorage()

    private val dataToTest = mutableListOf<Vehicle>()

    init {
        dataToTest.add(Vehicle(UUID.randomUUID(), "aaaa", Vehicle.TypeMotor.GASOLINA, UUID.randomUUID()))
        dataToTest.add(Vehicle(UUID.randomUUID(), "bbbb", Vehicle.TypeMotor.GASOLINA, UUID.randomUUID()))
        dataToTest.add(Vehicle(UUID.randomUUID(), "cccc", Vehicle.TypeMotor.GASOLINA, UUID.randomUUID()))
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