package services.database

import config.AppConflig
import mu.KotlinLogging
import java.sql.DriverManager

private val logger = KotlinLogging.logger { }

object DataBaseService {
    val db
        get() = DriverManager.getConnection(AppConflig.databaseUrl)


    init {
        logger.debug { "Init DataBaseService" }
        if (AppConflig.databaseDropTable) {
            dropTables()
        }
        createTables()
    }

    private fun dropTables() {
        val sql = "DROP TABLE IF EXISTS profesores"
        val sql1 = "DROP TABLE IF EXISTS modulos"
        logger.debug { "Drop Tables" }
        db.use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
                stm.executeUpdate(sql1)
            }
        }
    }

    private fun createTables() {
        var sql = """CREATE TABLE IF NOT EXISTS profesores 
        (id     INTEGER  PRIMARY KEY AUTOINCREMENT,
         nombre  TEXT     NOT NULL,
         fecha   TEXT     NOT NULL)
    """.trimIndent()
        var sql1 = """CREATE TABLE IF NOT EXISTS modulos
        (uuid    TEXT  PRIMARY KEY,
         nombre  REFERENCES profesores(nombre),
         curso   TEXT     NOT NULL,
         grado   TEXT     NOT NULL)
    """.trimIndent()
        logger.debug { "Create Tables" }
        db.use { it.createStatement().use { stm -> stm.executeUpdate(sql)} }
        db.use { it.createStatement().use { stm -> stm.executeUpdate(sql1)} }
    }

}