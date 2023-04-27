package repositories

import models.Coche
import services.database.DatabaseManager
import services.storage.CocheCsv
import services.storage.CocheJson
import java.util.*

object CocheRepository : ICocheRepository {
    private val database get() = DatabaseManager.db

    init {
        CocheCsv.load().forEach { create(it) }
    }

    override fun exportToJson() {
        CocheJson.save(findAll())
    }

    override fun findById(id: Long): Coche? {
        database.use { db ->
            db.prepareStatement("SELECT * FROM Coche WHERE id = ?").use { stmt ->
                stmt.setLong(1, id)
                stmt.executeQuery().use { res ->
                    return if (res.next()) {
                        Coche(
                            id = res.getLong(1),
                            marca = res.getString(2),
                            modelo = res.getString(3),
                            precio = res.getDouble(4),
                            tipoMotor = Coche.TipoMotor.valueOf(res.getString(5)),
                            conductor = ConductorRepository.findById(UUID.fromString(res.getString(6)))!!
                        )
                    } else null
                }
            }
        }
    }

    override fun findAll(): List<Coche> {
        val list = mutableListOf<Coche>()
        database.use { db ->
            db.prepareStatement("SELECT * FROM Coche").use { stmt ->
                stmt.executeQuery().use { res ->
                    while (res.next()) {
                        list.add(
                            Coche(
                                id = res.getLong(1),
                                marca = res.getString(2),
                                modelo = res.getString(3),
                                precio = res.getDouble(4),
                                tipoMotor = Coche.TipoMotor.valueOf(res.getString(5)),
                                conductor = ConductorRepository.findById(UUID.fromString(res.getString(6)))!!
                            )
                        )
                    }
                }
            }
        }
        return list
    }

    override fun create(entity: Coche): Coche {
        database.use { db ->
            db.prepareStatement(
                """
                INSERT INTO Coche (
                marca, modelo, precio, tipoMotor, conductorId
                ) VALUES (
                    ?, ?, ?, ?, ?
                )
            """.trimIndent()
            ).use { stmt ->
                stmt.setString(1, entity.marca)
                stmt.setString(2, entity.modelo)
                stmt.setDouble(3, entity.precio)
                stmt.setString(4, entity.tipoMotor.name)
                stmt.setString(5, entity.conductor.uuid.toString())
                stmt.executeUpdate()
                return entity
            }
        }
    }

    override fun update(entity: Coche): Coche? {
        database.use { db ->
            db.prepareStatement(
                """
                UPDATE Coche SET
                    marca = ?,
                    modelo = ?,
                    precio = ?,
                    tipoMotor = ?,
                    conductorId = ?
                WHERE id = ?
            """.trimIndent()
            ).use { stmt ->
                stmt.setString(1, entity.marca)
                stmt.setString(2, entity.modelo)
                stmt.setDouble(3, entity.precio)
                stmt.setString(4, entity.tipoMotor.name)
                stmt.setString(5, entity.conductor.uuid.toString())
                stmt.setLong(6, entity.id)
                return if (stmt.executeUpdate() > 0) entity else null
            }
        }
    }

    override fun delete(entity: Coche) = deleteById(entity.id)

    override fun deleteById(id: Long): Boolean {
        database.use { db ->
            db.prepareStatement("DELETE FROM Coche WHERE id = ?").use { stmt ->
                stmt.setLong(1, id)
                return (stmt.executeUpdate() > 0)
            }
        }
    }
}
