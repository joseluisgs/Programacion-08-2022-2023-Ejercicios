package config

import mu.KotlinLogging
import java.io.FileInputStream
import java.util.Properties

private val logger = KotlinLogging.logger {  }

object AppConfig {


    private var _dataInput: String = "data"
    val dataInput get()  = _dataInput

    private var _dataOutput: String = "data"
    val dataOutput get() = _dataOutput

    private var _databaseUrl: String = "jdbc:sqlite:coches.db"
    val databaseUrl get() = _databaseUrl

    private var _databaseDropTable: Boolean = false
    val databaseDropTable get() = _databaseDropTable


    init {
        loadProperties()
    }

    private fun loadProperties() {
        logger.debug { "loading properties" }
        val propertiesFile = ClassLoader.getSystemResource("program.properties").file

        val properties = Properties()

        properties.load(FileInputStream(propertiesFile))
        _dataInput = properties.getProperty("data.input", "data")
        _dataOutput = properties.getProperty("data.output", "data")
        _databaseUrl = properties.getProperty("database.url", "jdbc:sqlite:coches.db")
        _databaseDropTable = properties.getProperty("database.droptable", "false").toBoolean()
    }
}