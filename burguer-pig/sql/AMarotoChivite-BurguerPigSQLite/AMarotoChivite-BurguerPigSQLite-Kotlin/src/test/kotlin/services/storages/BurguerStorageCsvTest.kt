package services.storages

import config.ConfigApp
import models.Burguer
import models.Ingrediente
import models.LineaBurguer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDateTime
import java.util.*

class BurguerStorageCsvTest {

    private val typeExtension = "csv"

    private val storage = BurguerStorageCsv()

    private val dataToTest = mutableListOf<Burguer>()

    init {
        repeat(3){
            val lineasBurguer = mutableListOf<LineaBurguer>()
            val newUUIDburguer: UUID = UUID.randomUUID()

            val ingredeint = Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(), LocalDateTime.now(),true)
            lineasBurguer.add(LineaBurguer(0, newUUIDburguer,ingredeint.id, 2,ingredeint.price))

            dataToTest.add(Burguer(newUUIDburguer, "abcd",1, lineasBurguer))
        }
    }

    @Test
    fun writeFileToCsvTest() {
        storage.writeFile(dataToTest)

        val outputFile = File(ConfigApp.getPathDataOutput(typeExtension))
        assert(outputFile.exists())
    }

    @Test
    fun readFileOfCsvTest() {
        // Creamos el fichero para poderlo leer
        storage.writeFile(dataToTest)

        val result = storage.readFile()
        assertEquals(dataToTest.size, result.size)
    }

}