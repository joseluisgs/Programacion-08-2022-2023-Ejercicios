package repositories

import models.Conductor
import services.DatabaseManager
import java.time.LocalDate
import java.util.*

object ConductorRepositoryImpl : ConductorRepository {
    val db get() = DatabaseManager.db

    override fun findAll(): List<Conductor> {
        val lista = mutableListOf<Conductor>()
        db.use { connection ->
            val sql = "SELECT * FROM Conductor"
            val stmt = connection.prepareStatement(sql)
            stmt.use {
                val res = it.executeQuery()
                res.use { resultSet ->
                    while (resultSet.next()) {
                        lista.add(
                            Conductor(
                                uuid = UUID.fromString(resultSet.getString(1)),
                                nombre = resultSet.getString(2),
                                fechaCarnet = LocalDate.parse(resultSet.getString(3))
                            )
                        )
                    }
                }
            }
        }
        return lista
    }

    override fun findById(id: UUID): Conductor? {
        db.use { connection ->
            val sql = "SELECT * FROM Conductor WHERE uuid = ?"
            val stmt = connection.prepareStatement(sql)
            stmt.use {
                it.setString(1, id.toString())
                val res = it.executeQuery()
                res.use { resultSet ->
                    return if (resultSet.next()) {
                        Conductor(
                            uuid = UUID.fromString(resultSet.getString(1)),
                            nombre = resultSet.getString(2),
                            fechaCarnet = LocalDate.parse(resultSet.getString(3))
                        )
                    } else null
                }
            }
        }
    }

    override fun save(obj: Conductor): Conductor {
        db.use { connection ->
            val sql = "INSERT INTO Conductor(uuid, nombre, fechaCarnet) VALUES (?, ?, ?)"
            val stmt = connection.prepareStatement(sql)
            stmt.use {
                stmt.setString(1, obj.uuid.toString())
                stmt.setString(2, obj.nombre)
                stmt.setString(3, obj.fechaCarnet.toString())
                it.executeUpdate()
                return obj
            }
        }
    }

    override fun deleteById(id: UUID): Boolean {
        db.use { connection ->
            val sql = "DELETE FROM Conductor WHERE uuid = ?"
            val stmt = connection.prepareStatement(sql)
            stmt.use {
                stmt.setString(1, id.toString())
                val res = it.executeUpdate()
                return res > 0
            }
        }
    }

    override fun delete(obj: Conductor) = deleteById(obj.uuid)

    override fun update(obj: Conductor): Conductor? {
        db.use {connection ->
            val sql = "UPDATE Conductor SET uuid = ?, nombre = ?, fechaCarnet = ? WHERE uuid = ?"
            val stmt = connection.prepareStatement(sql)
            stmt.use {
                stmt.setString(1,obj.uuid.toString())
                stmt.setString(2,obj.nombre)
                stmt.setString(3,obj.fechaCarnet.toString())
                stmt.setString(4,obj.uuid.toString())
                val res = it.executeUpdate()
                return if (res > 0) obj
                else null
            }
        }
    }
}
