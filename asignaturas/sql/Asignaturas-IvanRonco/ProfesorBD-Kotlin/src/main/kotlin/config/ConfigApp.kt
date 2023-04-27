package config

import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.util.Properties

object ConfigApp {

    private val logger = KotlinLogging.logger {  }

    private var _APP_NAME: String = ""
    val APP_NAME get() = _APP_NAME
    private var _APP_AUTHOR: String = ""
    val APP_AUTHOR get() = _APP_AUTHOR
    private var _APP_VERSION: String = ""
    val APP_VERSION get() = _APP_VERSION
    private var _APP_DATA: String = ""
    val APP_DATA get() = _APP_DATA
    val myPath = System.getProperty("user.dir")+File.separator

    private var _APP_URL: String = ""
    val APP_URL get() = _APP_URL


    init {
        loadProperties()
        initStorage()
    }

    private fun loadProperties() {
        logger.debug { "Se cargan las propiedades" }
        val properties = Properties()
        properties.load(ConfigApp::class.java.getResourceAsStream("/config.properties"))
        _APP_NAME = properties.getProperty("APP_NAME") ?: "ProfesoresBD-Kotlin"
        _APP_AUTHOR = properties.getProperty("APP_AUTHOR") ?: "IvanRoncoCebadera"
        _APP_VERSION = properties.getProperty("APP_VERSION") ?: "1.0.0"
        _APP_DATA = properties.getProperty("APP_DATA") ?: "data"
        _APP_DATA = myPath+APP_DATA

        //Ahora cargo las propiedades para la database
        _APP_URL = properties.getProperty("APP_URL") ?: "jdbc:sqlite:profesores.db"
    }

    private fun initStorage() {
        logger.debug { "Se inicia el almacenamiento de ficheros" }
        val file = File(APP_DATA)
        if(!file.exists()){
            Files.createDirectory(file.toPath())
        }
    }
}