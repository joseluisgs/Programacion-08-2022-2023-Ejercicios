package service.database

import config.AppConfig
import mu.KotlinLogging
import java.sql.DriverManager

private val logger = KotlinLogging.logger {}

object DataBaseService {
    val db
        get() = DriverManager.getConnection(AppConfig.databaseUrl)

    init {
        logger.debug { "Iniciando DataBaseService" }
        if (AppConfig.databaseDropTable){
            dropTables()
        }
        createTables()
    }

    private fun dropTables() {
        val sqlI = "DROP TABLE IF EXISTS ingredientes"
        logger.debug { "Drop ingredientes table" }
        db.use {
            it.createStatement().use { stmt ->
                stmt.executeUpdate(sqlI)
            }
        }

        val sqlH = "DROP TABLE IF EXISTS hamburguesa"
        logger.debug { "Drop hamburguesa table" }
        db.use {
            it.createStatement().use { stmt ->
                stmt.executeUpdate(sqlH)
            }
        }

        val sqlL = "DROP TABLE IF EXISTS linea_hamburguesa"
        logger.debug { "Drop linea_hamburguesa table" }
        db.use {
            it.createStatement().use { stmt ->
                stmt.executeUpdate(sqlL)
            }
        }
    }
    private fun createTables() {
        val sqlI = """
            CREATE TABLE IF NOT EXISTS ingredientes (
                id  INTEGER PRIMARY KEY AUTOINCREMENT,
                uuid TEXT NOT NULL,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                disponible INTERGER NOT NULL,
                cantidad INTEGER NOT NULL,
                createdAt TEXT NOT NULL,
                updatedAt TEXT NOT NULL
            )
            """.trimIndent()
        logger.debug {"Creando tabla ingrediente"}
        db.use {
            it.createStatement().use { stmt ->
                stmt.executeUpdate(sqlI)
            }
        }


        val sqlH = """
            CREATE TABLE IF NOT EXISTS hamburguesa (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                uuid TEXT NOT NULL,
                name TEXT NOT NULL,
                precio REAL NOT NULL,
                cantidad INTEGER NOT NULL,
                createdAt TEXT NOT NULL,
                updatedAt TEXT NOT NULL
            )
        """.trimIndent()
        logger.debug {"Creando tabla hamburguesa"}
        db.use {
            it.createStatement().use { stmt ->
                stmt.executeUpdate(sqlH)
            }
        }


        val sqlL = """
            CREATE TABLE IF NOT EXISTS Linea_Hamburguesa (
                linea_id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_ingrediente INTEGER NOT NULL,
                id_hamburguesa INTEGER NOT NULL,
                precio_ingrediente REAL NOT NULL,
                cantidad_ingrediente INTEGER NOT NULL
            )
        """.trimIndent()
        logger.debug {"Creando tabla linea hamburguesa"}
        db.use {
            it.createStatement().use { stmt ->
                stmt.executeUpdate(sqlL)
            }
        }
    }
}