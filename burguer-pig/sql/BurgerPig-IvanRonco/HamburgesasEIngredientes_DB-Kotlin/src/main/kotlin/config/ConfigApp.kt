package config

import mu.KotlinLogging
import service.database.ConfigDatabase
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.util.Properties
import kotlin.io.path.Path

class ConfigApp(
    val configDatabase: ConfigDatabase
) {
    private val logger = KotlinLogging.logger {  }

    private val properties = Properties()

    val APP_DATA by lazy {
        properties.getProperty("APP_DATA") ?: "data"
    }
    val APP_AUTHOR by lazy {
        properties.getProperty("APP_AUTHOR") ?: "IvanRoncoCebadera"
    }
    val APP_VERSION by lazy {
        properties.getProperty("APP_VERSION") ?: "1.0.0"
    }

    init {
        loadProperties()
        initStorage()
        configDatabase.initDatabase()
    }

    private fun initStorage() {
        logger.debug { "Creamos, en caso de que no exista, la carpeta donde guardaremos los ficheros" }
        Files.createDirectories(Path(APP_DATA))
    }

    private fun loadProperties() {
        logger.debug { "Se cargan todas las propiedades de 'config.properties'" }
        properties.load(FileInputStream(ClassLoader.getSystemResource("config.properties").file))
    }
}