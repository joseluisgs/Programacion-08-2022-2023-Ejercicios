package services

import config.AppConfig
import java.sql.DriverManager

object DatabaseManager {
    val db get() = DriverManager.getConnection(AppConfig.dbUrl)

    init {
        db.use {
            var sql = "DROP TABLE IF EXISTS Conductor;"
            var stmt = it.prepareStatement(sql)
            stmt.use { it.executeUpdate() }

            sql = """
                CREATE TABLE IF NOT EXISTS Conductor(
                    uuid TEXT PRIMARY KEY,
                    nombre TEXT NOT NULL,
                    fechaCarnet TEXT NOT NULL
                ); 
            """.trimIndent()
            stmt = it.prepareStatement(sql)
            stmt.use { it.executeUpdate() }

            sql = "DROP TABLE IF EXISTS Coche;"
            stmt = it.prepareStatement(sql)
            stmt.use { it.executeUpdate() }

            sql = """
                CREATE TABLE IF NOT EXISTS Coche(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    marca TEXT NOT NULL,
                    modelo TEXT NOT NULL,
                    precio REAL NOT NULL,
                    motor TEXT NOT NULL,
                    conductorId TEXT NOT NULL REFERENCES Conductor (uuid)
                );
            """.trimIndent()
            stmt = it.prepareStatement(sql)
            stmt.use { it.executeUpdate() }
        }
    }
}
