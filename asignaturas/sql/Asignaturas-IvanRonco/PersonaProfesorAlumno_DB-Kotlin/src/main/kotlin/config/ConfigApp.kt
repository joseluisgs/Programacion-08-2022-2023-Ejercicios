package config

import mu.KotlinLogging
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.util.Properties

object ConfigApp {

    private var _APP_NAME = ""
    val APP_NAME: String get() = _APP_NAME
    private var _APP_AUTHOR = ""
    val APP_AUTHOR: String get() = _APP_AUTHOR
    private var _APP_DATA = ""
    val APP_DATA: String get() = _APP_DATA
    private var _APP_VERSION = ""
    val APP_VERSION: String get() = _APP_VERSION
    val PATH: String = System.getProperty("user.dir")+ File.separator

    private var _APP_URL = ""
    val APP_URL: String get() = _APP_URL
    private var _APP_INIT_DATA = true
    val APP_INIT_DATA: Boolean get() = _APP_INIT_DATA

    private val logger = KotlinLogging.logger {  }

    init{
        loadProperties()
        initStorage()
    }

    private fun initStorage() {
        logger.debug { "Se crea el directorio de storage, en caso de que no exista." }
        val file = File(APP_DATA)
        if(!file.exists()){
            Files.createDirectory(file.toPath())
        }
    }

    private fun loadProperties() {
        logger.debug { "Se cargan las propiedades del archivo config.properties." }
        val property = Properties()
        property.load(FileInputStream(ClassLoader.getSystemResource("config.properties").file))
        _APP_NAME = property.getProperty("APP_NAME") ?: "PersonaProfesorAlumno_DB-Kotlin"
        _APP_AUTHOR = property.getProperty("APP_AUTHOR") ?: "IvanRoncoCebadera"
        _APP_VERSION = property.getProperty("APP_VERSION") ?: "1.0.0"
        _APP_DATA = property.getProperty("APP_DATA") ?: "data"
        _APP_DATA = PATH + APP_DATA

        _APP_URL = property.getProperty("APP_URL") ?: "jdbc:sqlite:Database.db"
        _APP_INIT_DATA = (property.getProperty("APP_INIT_DATA") ?: "true") == "true"
    }
}