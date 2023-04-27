package repositories

import config.AppConflig
import models.Profesor
import mu.KotlinLogging
import java.sql.DriverManager
import java.sql.Statement
import java.time.LocalDate

object ProfesorRepository {
    private val logger = KotlinLogging.logger {}
    private val connection = DriverManager.getConnection(AppConflig.databaseUrl)

    init {
        var sql = """CREATE TABLE IF NOT EXISTS profesores 
        (id     INTEGER  PRIMARY KEY AUTOINCREMENT,
         nombre  TEXT     NOT NULL,
         fecha   TEXT     NOT NULL)
    """.trimIndent()
                connection.createStatement().use { statement ->
            statement.executeUpdate(sql).also { println("Table created") }
        }
    }

    fun findAll(): List<Profesor> {
        logger.debug { "findAll  REPOSITORIO" }
        var sql = "SELECT * FROM profesores"
        connection.createStatement().use { statement ->
            statement.executeQuery(sql).use { resultSet ->
                val profesores = mutableListOf<Profesor>()
                while (resultSet.next()) {
                    profesores.add(
                        Profesor(
                            id = resultSet.getLong("id"),
                            nombre = resultSet.getString("nombre"),
                            fehcaIncorpracion =LocalDate.parse(resultSet.getString("fecha"))
                        )
                    )
                }
                return profesores
            }
        }
    }

    fun findById(id: Long): Profesor? {
        var sql = "SELECT * FROM profesores WHERE id = ?"
        connection.prepareStatement(sql).use { statement ->
            // Le paso los parámetros
            statement.setLong(1, id)
            statement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return      Profesor(
                        id = resultSet.getLong("id"),
                        nombre = resultSet.getString("nombre"),
                        fehcaIncorpracion =LocalDate.parse(resultSet.getString("fecha"))
                    )
                }
            }
        }
        return null
    }

    fun save(entity: Profesor): Profesor {
        var sql = "INSERT INTO profesores (id,nombre,fecha) VALUES (null,?,?)"
        var myId = -99L

        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            .use { statement ->
                // Le paso los parámetros
                statement.setString(1, entity.nombre)
                statement.setString(2, entity.fehcaIncorpracion.toString())
                val res = statement.executeUpdate()
                // Ahora voy a por la clave generada
                val rs = statement.generatedKeys
                if (rs.next()) {
                    myId = rs.getLong(1)
                }
            }
        return entity.copy(id = myId)
    }

    fun update(entity: Profesor): Profesor? {
        var sql = "UPDATE profesores SET nombre = ?, fecha = ? WHERE id = ?"

        connection.prepareStatement(sql).use { statement ->
            // Le paso los parámetros
            statement.setString(1, entity.nombre)
            statement.setString(2, entity.fehcaIncorpracion.toString())
            statement.setLong(3, entity.id)
            val res = statement.executeUpdate()
            if (res == 1) return entity
        }
        return null
    }

    fun deleteById(id: Long): Boolean {
        var sql = "DELETE FROM profesores WHERE id = ?"

        connection.prepareStatement(sql).use { statement ->
            // Le paso los parámetros
            statement.setLong(1, id)
            val res = statement.executeUpdate()
            println("Borrado $res filas")
            if (res == 1) return true
        }
        return false
    }
}
