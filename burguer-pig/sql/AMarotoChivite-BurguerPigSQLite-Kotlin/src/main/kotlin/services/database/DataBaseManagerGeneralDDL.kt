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
        logger.debug { "DataManager: Creando Tabla Burguer" }

        var sql = """
            CREATE TABLE IF NOT EXISTS BurguerTable(
                uuid        TEXT    NOT NULL PRIMARY KEY,
                name        TEXT    NOT NULL,
                stock       INTEGER NOT NULL
            );
        """.trimIndent()
        db().use {
            it.createStatement().use { stm -> stm.executeUpdate(sql) }
        }

        logger.debug { "DataManager: Creando Tabla LineaBurguer" }

        sql = """
            CREATE TABLE IF NOT EXISTS LineaBurguerTable(
                linea_id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                burguer_uuid          TEXT    NOT NULL,
                ingrediente_id        TEXT    NOT NULL,
                ingrediente_quantity  INTEGER NOT NULL,
                ingrediente_price     INTEGER NOT NULL
            );
        """.trimIndent()
        db().use {
            it.createStatement().use { stm -> stm.executeUpdate(sql) }
        }

        logger.debug { "DataManager: Creando Tabla Ingrediente" }

        sql = """
            CREATE TABLE IF NOT EXISTS IngredienteTable(
                id            INTEGER PRIMARY KEY AUTOINCREMENT,
                name          INTEGER NOT NULL,
                price         REAL    NOT NULL,
                stock         INTEGER NOT NULL,
                created_at     TEXT    NOT NULL,
                updated_at     TEXT    NOT NULL,
                avaliable     INTEGER NOT NULL
            );
        """.trimIndent()
        db().use {
            it.createStatement().use { stm -> stm.executeUpdate(sql) }
        }
    }

    override
    fun dropTables() {
        logger.debug { "DataManager: Eliminando Tablas" }

        var sql = "DROP TABLE IF EXISTS BurguerTable"
        db().use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
            }
        }

        sql = "DROP TABLE IF EXISTS LineaBurguerTable"
        db().use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
            }
        }

        sql = "DROP TABLE IF EXISTS IngredienteTable"
        db().use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
            }
        }
    }
}