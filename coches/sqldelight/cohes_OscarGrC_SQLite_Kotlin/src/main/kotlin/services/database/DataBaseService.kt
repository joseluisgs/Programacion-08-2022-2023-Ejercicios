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
        val sql = "DROP TABLE IF EXISTS coches"
        val sql1 = "DROP TABLE IF EXISTS conductores"
        logger.debug { "Drop Tables" }
        db.use {
            it.createStatement().use { stm ->
                stm.executeUpdate(sql)
                stm.executeUpdate(sql1)
            }
        }
    }

    private fun createTables() {
        var sql = """CREATE TABLE IF NOT EXISTS conductores
        (uuid    TEXT  PRIMARY KEY,
         nombre  TEXT     NOT NULL,
         fecha   TEXT     NOT NULL)
    """.trimIndent()
        var sql1 = """CREATE TABLE IF NOT EXISTS coches 
        (id     INTEGER  PRIMARY KEY AUTOINCREMENT,
         marca  TEXT     NOT NULL,
         modelo TEXT     NOT NULL,
         precio DOUBLE   NOT NULL,
         motor  TEXT     NOT NULL)
    """.trimIndent()
        logger.debug { "Create Tables" }
        db.use { it.createStatement().use { stm -> stm.executeUpdate(sql)} }
        db.use { it.createStatement().use { stm -> stm.executeUpdate(sql1)} }
    }

}