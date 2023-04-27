package services.database

import config.ConfigApp
import services.database.base.IDataBaseManagerDDL
import java.sql.Connection
import java.sql.DriverManager

private val logger = mu.KotlinLogging.logger { }

object DataBaseManagerGeneralDDL : IDataBaseManagerDDL {

    private val isExecuteTest = ConfigApp.getIsExcuteTest()

    private fun db(): Connection {
        return DriverManager.getConnection(ConfigApp.getUrlDbConection())
    }

    init {
        // La base de datos se crea sola gracias al DriverManager
        if (isExecuteTest) {
            logger.debug { "DataManager: Iniciando DataBase en Test" }
        } else {
            logger.debug { "DataManager: Iniciando DataBase en Producción" }
        }

        // Borramos tablas si está en la config=true
        if (ConfigApp.getDropTables()) {
            dropTables()
        }
        createTables()
    }
 
    override
    fun createTables() {
        logger.debug { "DataManager: Creando Tabla Profesor" }

        var sql = """
            CREATE TABLE IF NOT EXISTS ProfesorTable(
                id          INTEGER   NOT NULL PRIMARY KEY AUTOINCREMENT,
                name        TEXT      NOT NULL,
                date_init   TEXT      NOT NULL
            );
        """.trimIndent()
        db().use {
            it.createStatement().use { stm -> stm.executeUpdate(sql) }
        }

        logger.debug { "DataManager: Creando Tabla Modulo" }

        sql = """
            CREATE TABLE IF NOT EXISTS ModuloTable(
                uuid    TEXT    PRIMARY KEY NOT NULL,
                name    TEXT    NOT NULL,
                curso   INTEGER NOT NULL,
                grado   TEXT    NOT NULL
            );
        """.trimIndent()
        db().use {
            it.createStatement().use { stm -> stm.executeUpdate(sql) }
        }

        logger.debug { "DataManager: Creando Tabla Docencia" }

        sql = """
            CREATE TABLE IF NOT EXISTS DocenciaTable(
                id_profesor   INTEGER    NOT NULL,
                uuid_modulo   TEXT       NOT NULL,
                curso         INTEGER    NOT NULL,
                grado         TEXT       NOT NULL,
                PRIMARY KEY (id_profesor, uuid_modulo)
            );
        """.trimIndent()
        db().use {
            it.createStatement().use { stm -> stm.executeUpdate(sql) }
        }
    }

    override
    fun dropTables() {
        logger.debug { "DataManager: Eliminando Tablas" }

        var sql = "DROP TABLE IF EXISTS ProfesorTable"
        db().use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
            }
        }

        sql = "DROP TABLE IF EXISTS ModuloTable"
        db().use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
            }
        }

        sql = "DROP TABLE IF EXISTS DocenciaTable"
        db().use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
            }
        }
    }
}