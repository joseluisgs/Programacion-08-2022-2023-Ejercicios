package repositories

import models.Coche
import models.Conductor
import services.DatabaseManager
import storage.CocheCsvService
import storage.CocheJsonService
import java.sql.Statement
import java.util.*

object CocheRepositoryImpl : CocheRepository {

    val db get() = DatabaseManager.db

    override fun leerCsv(): List<Coche> {
        return CocheCsvService.cargar()
    }

    override fun escribirJson() {
        CocheJsonService.guardar(findAll())
    }

    override fun findAll(): List<Coche> {
        val lista = mutableListOf<Coche>()
        db.use { connection ->
            val sql = "SELECT * FROM Coche"
            val stmt = connection.prepareStatement(sql)
            stmt.use {
                val res = it.executeQuery()
                res.use { resultSet ->
                    while (resultSet.next()) {
                        lista.add(
                            Coche(
                                id = resultSet.getLong(1),
                                marca = resultSet.getString(2),
                                modelo = resultSet.getString(3),
                                precio = resultSet.getDouble(4),
                                motor = Coche.TipoMotor.valueOf(resultSet.getString(5)),
                                conductor = Conductor(UUID.fromString(resultSet.getString(6)))
                            )
                        )
                    }
                }
            }
        }
        return lista
    }

    override fun findById(id: Long): Coche? {
        return findAll().find { it.id == id }
    }

    override fun save(obj: Coche): Coche {
        db.use {
            val sql = "INSERT INTO Coche(marca,modelo,precio,motor,conductorId) VALUES (?,?,?,?,?)"
            val stmt = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            stmt.use {
                stmt.setString(1, obj.marca)
                stmt.setString(2, obj.modelo)
                stmt.setDouble(3, obj.precio)
                stmt.setString(4, obj.motor.toString())
                stmt.setString(5, obj.conductor.uuid.toString())
                stmt.executeUpdate()
                return obj
            }
        }
    }

    override fun deleteById(id: Long): Boolean {
        db.use {
            val sql = "DELETE FROM Coche WHERE id = ?"
            val stmt = db.prepareStatement(sql)
            stmt.use {
                stmt.setLong(1, id)
                val res = stmt.executeUpdate()
                return res > 0
            }
        }
    }

    override fun delete(obj: Coche): Boolean {
        return deleteById(obj.id)
    }

    override fun update(obj: Coche): Coche? {
        db.use {
            val sql =
                "UPDATE Coche SET id = ?, marca = ?, modelo = ?, precio = ?, motor = ?, conductorId = ? WHERE id = ?"
            val stmt = db.prepareStatement(sql)
            stmt.use {
                stmt.setLong(1, obj.id)
                stmt.setString(2, obj.marca)
                stmt.setString(3, obj.modelo)
                stmt.setDouble(4, obj.precio)
                stmt.setString(5, obj.motor.toString())
                stmt.setString(6, obj.conductor.uuid.toString())
                val res = stmt.executeUpdate()
                return if (res > 0) obj
                else null
            }
        }
    }
}
