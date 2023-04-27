package repositories

import models.Conductor
import services.database.DatabaseManager
import java.time.LocalDate
import java.util.UUID

object ConductorRepository : ICrudRepository<Conductor, UUID> {
    private val database get() = DatabaseManager.db

    init {
        listOf(
            Conductor(UUID.randomUUID(), "Conductor1", LocalDate.parse("2020-01-01")),
            Conductor(UUID.randomUUID(), "Conductor2", LocalDate.parse("2020-01-02")),
            Conductor(UUID.randomUUID(), "Conductor3", LocalDate.parse("2020-01-03")),
            Conductor(UUID.randomUUID(), "Conductor4", LocalDate.parse("2020-01-04")),
            Conductor(UUID.randomUUID(), "Conductor5", LocalDate.parse("2020-01-05")),
        ).forEach { create(it) }
    }

    override fun findById(id: UUID): Conductor? {
        database.use { db ->
            db.prepareStatement("SELECT * FROM Conductor WHERE uuid = ?").use { stmt ->
                stmt.setString(1, id.toString())
                stmt.executeQuery().use { res ->
                    return if (res.next()) {
                        Conductor(
                            uuid = UUID.fromString(res.getString(1)),
                            nombre = res.getString(2),
                            fechaCarnet = LocalDate.parse(res.getString(3))
                        )
                    } else null
                }
            }
        }
    }

    override fun findAll(): List<Conductor> {
        val list = mutableListOf<Conductor>()
        database.use { db ->
            db.prepareStatement("SELECT * FROM Conductor").use { stmt ->
                stmt.executeQuery().use { res ->
                    while (res.next()) {
                        list.add(
                            Conductor(
                                uuid = UUID.fromString(res.getString(1)),
                                nombre = res.getString(2),
                                fechaCarnet = LocalDate.parse(res.getString(3))
                            )
                        )
                    }
                }
            }
        }
        return list
    }

    override fun deleteById(id: UUID): Boolean {
        database.use { db ->
            db.prepareStatement("DELETE FROM Conductor WHERE uuid = ?").use { stmt ->
                stmt.setString(1, id.toString())
                return (stmt.executeUpdate() > 0)
            }
        }
    }

    override fun delete(entity: Conductor) = deleteById(entity.uuid)

    override fun update(entity: Conductor): Conductor? {
        database.use { db ->
            db.prepareStatement("UPDATE Conductor SET nombre = ?, fechaCarnet = ? WHERE uuid = ?")
                .use { stmt ->
                    stmt.setString(1, entity.nombre)
                    stmt.setString(2, entity.fechaCarnet.toString())
                    stmt.setString(3, entity.uuid.toString())
                    return if (stmt.executeUpdate() > 0) entity else null
                }
        }
    }

    override fun create(entity: Conductor): Conductor {
        database.use { db ->
            db.prepareStatement(
                """
                INSERT INTO Conductor (
                    uuid, nombre, fechaCarnet
                ) VALUES (
                    ?, ?, ?
                )
            """.trimIndent()
            ).use { stmt ->
                stmt.setString(1, entity.uuid.toString())
                stmt.setString(2, entity.nombre)
                stmt.setString(3, entity.fechaCarnet.toString())
                stmt.executeUpdate()
                return entity
            }

        }
    }
}
