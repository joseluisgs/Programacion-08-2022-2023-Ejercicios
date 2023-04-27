package repositories

import config.AppConflig
import models.Coche
import models.Conductor
import models.TipoMotor
import mu.KotlinLogging
import validators.validate
import java.sql.DriverManager
import java.sql.Statement
import java.time.LocalDate
import java.util.*

object ConductorRepository {
    private val logger = KotlinLogging.logger {}
    private val connection = DriverManager.getConnection(AppConflig.databaseUrl)
    init {
        var sql = """CREATE TABLE IF NOT EXISTS conductores
        (uuid    TEXT  PRIMARY KEY,
         nombre  TEXT     NOT NULL,
         fecha   TEXT     NOT NULL)
    """.trimIndent()
        connection.createStatement().use { statement ->
            statement.executeUpdate(sql).also { println("Table created") }
        }
    }

    fun findAll(): List<Conductor> {
        logger.debug { "findAll  REPOSITORIO" }
        var sql = "SELECT * FROM conductores"
        connection.createStatement().use { statement ->
            statement.executeQuery(sql).use { resultSet ->
                val conductores = mutableListOf<Conductor>()
                while (resultSet.next()) {
                    conductores.add(
                        Conductor(
                            uuid = UUID.fromString(resultSet.getString("uuid")),
                            nombre = resultSet.getString("nombre"),
                            fechaCarnet = LocalDate.parse(resultSet.getString("fecha"))
                        )
                    )
                }
                return conductores
            }
        }
    }

    fun findByUUID(uuid:String): Conductor? {
        var sql = "SELECT * FROM conductores WHERE uuid = ?"
        connection.prepareStatement(sql).use { statement ->
            // Le paso los parámetros
            statement.setString(1, uuid)
            statement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                 return Conductor(
                        uuid = UUID.fromString(resultSet.getString("uuid")),
                        nombre = resultSet.getString("nombre"),
                        fechaCarnet = LocalDate.parse(resultSet.getString("fecha"))
                    )
                }
            }
        }
        return null
    }

    fun save(entity: Conductor): Conductor {
        var sql = "INSERT INTO conductores (uuid,nombre,fecha) VALUES (?,?,?)"
        entity.validate()
        connection.prepareStatement(sql)
            .use { statement ->
                // Le paso los parámetros
                statement.setString(1, entity.uuid.toString())
                statement.setString(2, entity.nombre)
                statement.setString(3, entity.fechaCarnet.toString())
                val res = statement.executeUpdate()
            }
        return entity
    }

    fun update(entity: Conductor): Conductor? {
        entity.validate()
        var sql = "UPDATE conductores SET nombre = ?, fecha = ? WHERE uuid = ?"
        connection.prepareStatement(sql).use { statement ->
            // Le paso los parámetros
            statement.setString(1, entity.nombre)
            statement.setString(2, entity.fechaCarnet.toString())
            statement.setString(3, entity.uuid.toString())
            val res = statement.executeUpdate()
            if (res == 1) return entity
        }
        return null
    }

    fun deleteByUUID(uuid: String): Boolean {
        var sql = "DELETE FROM conductores WHERE uuid = ?"
        connection.prepareStatement(sql).use { statement ->
            // Le paso los parámetros
            statement.setString(1, uuid)
            val res = statement.executeUpdate()
            println("Borrado $res filas")
            if (res == 1) return true
        }
        return false
    }
}
