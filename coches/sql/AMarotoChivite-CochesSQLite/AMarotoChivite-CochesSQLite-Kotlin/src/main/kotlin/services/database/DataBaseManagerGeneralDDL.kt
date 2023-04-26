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
        logger.debug { "DataManager: Creando Tabla Vehicle" }

        var sql = """
            CREATE TABLE IF NOT EXISTS VehicleTable(
                uuid         TEXT    NOT NULL PRIMARY KEY,
                model        TEXT    NOT NULL,
                type_motor   TEXT    NOT NULL,
                fk_conductor TEXT    NOT NULL
            );
        """.trimIndent()
        db().use {
            it.createStatement().use { stm -> stm.executeUpdate(sql) }
        }

        logger.debug { "DataManager: Creando Tabla Conductor" }

        sql = """
            CREATE TABLE IF NOT EXISTS ConductorTable(
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                uuid        TEXT    NOT NULL,
                name        TEXT    NOT NULL
            );
        """.trimIndent()
        db().use {
            it.createStatement().use { stm -> stm.executeUpdate(sql) }
        }
    }

    override
    fun dropTables() {
        logger.debug { "DataManager: Eliminando Tablas" }

        var sql = "DROP TABLE IF EXISTS VehicleTable"
        db().use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
            }
        }

        sql = "DROP TABLE IF EXISTS ConductorTable"
        db().use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
            }
        }
    }
}