package services.database

import config.AppConfig
import mu.KotlinLogging
import java.sql.Connection
import java.sql.DriverManager
import kotlin.math.log

object CochesDataBaseService {

    private val logger = KotlinLogging.logger {}

    val db: Connection get() = DriverManager.getConnection(AppConfig.databaseUrl)

    init {
        logger.debug { "Iniciando base de datos" }
        if (AppConfig.databaseDropTable){
            dropTable()
        }
        createTables()
    }

    private fun createTables() {
        val sql = """CREATE TABLE IF NOT EXISTS coches(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            marca TEXT NOT NULL,
            modelo TEXT NOT NULL,
            precio REAL NOT NULL,
            tipoMotor TEXT NOT NULL
            )""".trimIndent()

        val sql2 = """CREATE TABLE IF NOT EXISTS conductores(
            uuid UUID,
            nombre TEXT NOT NULL,
            fechaCarnet TEXT NOT NULL )
            """.trimIndent()

        logger.debug { "Create tables" }

        db.use {
            it.createStatement().use {
                stm-> stm.executeUpdate(sql)
                stm.executeUpdate(sql2)
            }
        }
    }

    private fun dropTable() {
        val sql = """DROP TABLE IF EXISTS coches""".trimIndent()
        val sql2 = """DROP TABLE IF EXISTS conductores""".trimIndent()

        logger.debug { "Drop tables" }
        db.use {
            it.createStatement().use {
                stm -> stm.executeUpdate(sql)
                stm.executeUpdate(sql2)
            }
        }
    }
}