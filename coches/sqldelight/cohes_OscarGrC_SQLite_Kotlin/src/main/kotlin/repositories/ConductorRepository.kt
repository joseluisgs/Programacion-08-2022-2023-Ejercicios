package repositories

import config.AppConflig
import models.Coche
import models.Conductor
import models.TipoMotor
import mu.KotlinLogging
import services.database.DataBaseService
import validators.validate
import java.sql.DriverManager
import java.sql.Statement
import java.time.LocalDate
import java.util.*

object ConductorRepository {
    private val logger = KotlinLogging.logger {}

    fun findAll(): List<Conductor> {
        logger.debug { "findAll  REPOSITORIO" }
        var sql = "SELECT * FROM conductores"
        DataBaseService.db.createStatement().use { statement ->
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
        DataBaseService.db.prepareStatement(sql).use { statement ->
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
        DataBaseService.db.prepareStatement(sql)
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
        DataBaseService.db.prepareStatement(sql).use { statement ->
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
        DataBaseService.db.prepareStatement(sql).use { statement ->
            // Le paso los parámetros
            statement.setString(1, uuid)
            val res = statement.executeUpdate()
            println("Borrado $res filas")
            if (res == 1) return true
        }
        return false
    }
}
