package config

import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.exists

private val logger = mu.KotlinLogging.logger {}

object ConfigApp {

    private val RESOURCE_PATH_MAIN =
        "${System.getProperty("user.dir")}${File.separator}src${File.separator}main${File.separator}resources${File.separator}"

    // Utilizo la técnica "Backing Properties", mediante "getters" para encapsular nuestra configuración

    private var _dataFatherPath: String = "" // Ruta de la carpeta padre de los datos
    fun getDataFatherPath(): String {
        return _dataFatherPath
    }

    // Doy la posibilidad de asignar la extensión que queramos
    private var _dataOutputPath: String = "" // Ruta de la carpeta donde exportaremos datos
    fun getPathDataOutput(nameFile: String, extension: String): String {
        return _dataOutputPath + "${nameFile}.${extension}"
    }

    private var _dataInputPath: String = "" // Ruta de la carpeta donde importaremos datos
    fun getPathDataInput(nameFile: String, extension: String): String {
        return _dataInputPath + "${nameFile}.${extension}"
    }

    private var _dbUrlConection: String = "" // URL donde nos conectaremos a la base de datos
    fun getUrlDbConection(): String {
        return _dbUrlConection
    }

    private var _deleteDb: Boolean = false // Si permitimos borrar las tablas
    fun getDeleteDb(): Boolean {
        return _deleteDb
    }

    private var _isExcuteTest: Boolean = false // Si ejecutamos para Test
    fun getIsExcuteTest(): Boolean {
        return _isExcuteTest
    }

    init {
        loadProperties()
        initStorage()
    }

    // En primer lugar comprobamos si existe nuestra carpeta principal, donde almacenaremos nuestra gestión
    private fun initStorage() {
        // Creamos el directorio padre si no existe
        if (!Paths.get(_dataFatherPath).exists()) {
            logger.debug { "Creando carpeta padre de Datos" }
            Files.createDirectories(Paths.get(_dataFatherPath))
        }
    }

    // Cargamos siempre en primer lugar el fichero properties
    private fun loadProperties() {
        logger.debug { "Cargando properties (configuración)" }
        val properties = Properties()

        // Accedemos al fichero de propiedades mediante su nombre
        val propertiesFile = ClassLoader.getSystemResource("config.properties").file

        // Cargamos el fichero a la clase Properties
        properties.load(FileInputStream(propertiesFile))

        _dataFatherPath = "$RESOURCE_PATH_MAIN${properties.getProperty("data.father", "data")}${File.separator}"

        // Recogemos la configuración del fichero, para ser llamadas desde lso campos de ConfigApp
        // El segundo parámetro es por defecto! Por si no es capaz de leer el properties
        _dataOutputPath = _dataFatherPath + properties.getProperty("data.output", "output") // Escritura
        _dataInputPath = _dataFatherPath + properties.getProperty("data.input", "input") // Lectura

        _dbUrlConection = properties.getProperty("db.url.connection", "jdbc:sqlite:database.db")

        _deleteDb = properties.getProperty("db.delete", "false").toBoolean()
    }
}