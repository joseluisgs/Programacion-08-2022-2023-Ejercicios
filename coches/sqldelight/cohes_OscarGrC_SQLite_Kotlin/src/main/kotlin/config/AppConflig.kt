package config

import java.io.FileInputStream
import java.util.*

object AppConflig {
    lateinit var databaseUrl: String
    lateinit var outputData: String
    lateinit var inputData: String
    var databaseDropTable: Boolean = true

    init {
        // Leer el las propiedades
        println("Leyendo las propiedades")
        val fileProperties = ClassLoader.getSystemResource("config.properties").file
        val properties = Properties()
        properties.load(FileInputStream(fileProperties))
        databaseUrl = properties.getProperty("database.url", "jdbc:sqlite:database.db")
        outputData = properties.getProperty("output.data", "data")
        inputData = properties.getProperty("data.input", "data")
        databaseDropTable = properties.getProperty("database.droptable", "false").toBoolean()
    }
}

