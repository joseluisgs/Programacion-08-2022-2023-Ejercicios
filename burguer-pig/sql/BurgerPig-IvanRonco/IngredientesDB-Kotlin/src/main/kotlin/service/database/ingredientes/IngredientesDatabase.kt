package service.database.ingredientes

import config.ConfigApp
import mu.KotlinLogging
import java.sql.DriverManager

object IngredientesDatabase {

    val config = ConfigApp

    val logger = KotlinLogging.logger {  }

    val connection get() = DriverManager.getConnection(config.APP_URL)

    init {

        connection.use {
            it.prepareStatement("DROP TABLE IF EXISTS INGREDIENTES").use {stm ->
                stm.executeUpdate()
            }
        }

        val sqlCreateTable =
            """
                CREATE TABLE INGREDIENTES(
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                UUID TEXT UNIQUE NOT NULL,
                NOMBRE TEXT NOT NULL,
                PRECIO REAL NOT NULL,
                CANTIDAD INTEGER NOT NULL,
                CREATED_AT TEXT NOT NULL,
                UPDATED_AT TEXT NOT NULL,
                DISPONIBLE INTEGER NOT NULL
                )""".trimIndent()
        connection.use {
            it.prepareStatement(sqlCreateTable).use {stm ->
                stm.executeUpdate()
            }
        }
    }
}