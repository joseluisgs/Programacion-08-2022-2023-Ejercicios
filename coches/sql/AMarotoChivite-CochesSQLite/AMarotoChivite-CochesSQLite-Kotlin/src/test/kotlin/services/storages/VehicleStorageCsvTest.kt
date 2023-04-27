package services.storages

import config.ConfigApp
import models.Vehicle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class VehicleStorageCsvTest {

    private val typeExtension = "csv"

    private val storage = VehicleStorage()

    private val dataToTest = mutableListOf<Vehicle>()

    init {
        dataToTest.add(Vehicle(UUID.randomUUID(), "aaaa", Vehicle.TypeMotor.GASOLINA, UUID.randomUUID()))
        dataToTest.add(Vehicle(UUID.randomUUID(), "bbbb", Vehicle.TypeMotor.GASOLINA, UUID.randomUUID()))
        dataToTest.add(Vehicle(UUID.randomUUID(), "cccc", Vehicle.TypeMotor.GASOLINA, UUID.randomUUID()))
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