package repositories

import config.AppConflig
import models.Grado
import models.Modulo
import mu.KotlinLogging
import org.simpleframework.xml.Element
import services.database.DataBaseService
import validators.validate
import java.sql.DriverManager
import java.time.LocalDate
import java.util.*

object ModuloRepository {
    private val logger = KotlinLogging.logger {}

    fun findAll(): List<Modulo> {
        logger.debug { "findAll  REPOSITORIO" }
        var sql = "SELECT * FROM modulos"
        DataBaseService.db.createStatement().use { statement ->
            statement.executeQuery(sql).use { resultSet ->
                val modulos = mutableListOf<Modulo>()
                while (resultSet.next()) {
                    modulos.add(
                        Modulo(
                            uuid = UUID.fromString(resultSet.getString("uuid")),
                            nombre = resultSet.getString("nombre"),
                            curso = resultSet.getString("curso"),
                            grado = Grado.valueOf(resultSet.getString("grado"))))
                }
                return modulos
            }
        }
    }

    fun findByUUID(uuid:String): Modulo? {
        var sql = "SELECT * FROM modulos WHERE uuid = ?"
        DataBaseService.db.prepareStatement(sql).use { statement ->
            // Le paso los parámetros
            statement.setString(1, uuid)
            statement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                 return     Modulo(
                     uuid = UUID.fromString(resultSet.getString("uuid")),
                     nombre = resultSet.getString("nombre"),
                     curso = resultSet.getString("curso"),
                     grado = Grado.valueOf(resultSet.getString("grado")))
                }
            }
        }
        return null
    }

    fun save(entity: Modulo): Modulo {
        var sql = "INSERT INTO modulos (uuid,nombre,curso,grado) VALUES (?,?,?,?)"
        entity.validate()
        DataBaseService.db.prepareStatement(sql)
            .use { statement ->
                // Le paso los parámetros
                statement.setString(1, entity.uuid.toString())
                statement.setString(2, entity.nombre)
                statement.setString(3, entity.curso.toString())
                statement.setString(4, entity.grado.name)
                val res = statement.executeUpdate()
            }
        return entity
    }

    fun update(entity: Modulo): Modulo? {
        entity.validate()
        var sql = "UPDATE modulos SET  curso = ?,grado = ? WHERE uuid = ?"
        DataBaseService.db.prepareStatement(sql).use { statement ->
            // Le paso los parámetros
            statement.setString(1, entity.curso)
            statement.setString(2, entity.grado.name)
            statement.setString(3, entity.uuid.toString())
            val res = statement.executeUpdate()
            if (res == 1) return entity
        }
        return null
    }

    fun deleteByUUID(uuid: String): Boolean {
        var sql = "DELETE FROM modulos WHERE uuid = ?"
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



