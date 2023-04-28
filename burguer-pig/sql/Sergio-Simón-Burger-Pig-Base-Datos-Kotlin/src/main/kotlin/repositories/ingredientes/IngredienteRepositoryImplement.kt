package repositories.ingredientes

import models.Ingrediente
import mu.KotlinLogging
import service.database.DataBaseService
import java.sql.Statement
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger{}

class IngredienteRepositoryImplement: IngredienteRepository {

    override fun findAll(): Iterable<Ingrediente> {
        logger.debug { "Cargando los ingrediente desde una base de datos" }

        val items = mutableListOf<Ingrediente>()

        val sql = "SELECT * FROM ingredientes"
        DataBaseService.db.use {
            it.prepareStatement(sql).use { stm ->
                val rs = stm.executeQuery()
                while (rs.next()) {
                    items.add(
                        Ingrediente(
                            id = rs.getLong("id"),
                            uuid = UUID.fromString(rs.getString("uuid")),
                            name = rs.getString("name"),
                            price = rs.getDouble("price"),
                            disponible = rs.getBoolean("disponible"),
                            cantidad = rs.getInt("cantidad"),
                            createdAt = LocalDateTime.parse(rs.getString("createdAt")),
                            updatedAt = LocalDateTime.parse(rs.getString("updatedAt"))
                        )
                    )
                }
            }
        }
        return items
    }

    override fun findById(id: Long): Ingrediente? {
        logger.debug { "Buscando ingrediente con el id: $id" }

        var item: Ingrediente? = null

        val sql = "SELECT * FROM ingredientes WHERE id = ?"

        DataBaseService.db.use {
            it.prepareStatement(sql).use { stm ->
                stm.setLong(1, id)
                val rs = stm.executeQuery()
                if (rs.next()) {
                    item = Ingrediente(
                        id = rs.getLong("id"),
                        uuid = UUID.fromString(rs.getString("uuid")),
                        name = rs.getString("name"),
                        price = rs.getDouble("price"),
                        disponible = rs.getBoolean("disponible"),
                        cantidad = rs.getInt("cantidad"),
                        createdAt = LocalDateTime.parse(rs.getString("createdAt")),
                        updatedAt = LocalDateTime.parse(rs.getString("updatedAt"))
                    )
                }
            }
        }
        return item
    }

    override fun existsById(id: Long): Boolean {
        logger.debug { "Buscando si existe el ingrediente con id: $id" }
        return findById(id) != null
    }

    override fun save(entity: Ingrediente): Ingrediente {
        logger.debug { "Guardando ingrediente en la base de datos" }

        // Revisar si existe el ingrediente para no crear uno nuevo
        return if (existsById(entity.id)){
            update(entity)
        } else {
            create(entity)
        }
    }

    private fun create(entity: Ingrediente): Ingrediente {
        logger.debug { "Creando ingrediente en la base de datos" }

        val createdTime = LocalDateTime.now()
        var newId: Long = 0

        val sql = """
            INSERT INTO ingredientes VALUES (NULL, ?,?,?,?,?,?,?)
        """.trimIndent()

        DataBaseService.db.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                stm.setString(1, entity.uuid.toString())
                stm.setString(2, entity.name)
                stm.setDouble(3, entity.price)
                stm.setBoolean(4, entity.disponible)
                stm.setInt(5, entity.cantidad)
                stm.setString(6, createdTime.toString())
                stm.setString(7, createdTime.toString())

                stm.executeUpdate()

                val claves = stm.generatedKeys
                if(claves.next()){
                    newId = claves.getLong(1)
                }
            }
        }
        // Para recuperar el ingrediente creado con los parámetros nuevos
        return entity.copy(id = newId, createdAt = createdTime, updatedAt = createdTime)
    }

    override fun update(entity: Ingrediente): Ingrediente {
        logger.debug { "Actualizando ingrediente en la base de datos"}

        val update = LocalDateTime.now()

        // No se actualiza los id, UUID y el createdAt. SON VALORES FIJOS
        val sql = """
            UPDATE ingredientes SET name = ?, price = ?, disponible = ?, cantidad = ?,updatedAt = ? WHERE id = ?
        """.trimIndent()
        DataBaseService.db.use {
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, entity.name)
                stm.setDouble(2, entity.price)
                stm.setBoolean(3, entity.disponible)
                stm.setInt(4, entity.cantidad)
                stm.setString(5, update.toString())
                stm.setLong(6, entity.id)

                stm.executeUpdate()
            }
        }
        // Para recuperar el ingrediente actualizado con los parámetros nuevos
        return entity.copy(updatedAt = update)
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "Borrando ingrediente con id: $id" }

        var res = 0

        val sql = """
            DELETE FROM ingredientes WHERE id =?
        """.trimIndent()
        DataBaseService.db.use {
            it.prepareStatement(sql).use { stm ->
                stm.setLong(1, id)

                res= stm.executeUpdate()
            }
        }
        return res == 1
    }

    override fun delete(entity: Ingrediente): Boolean {
        logger.debug { "Borrando el ingrediente: $entity" }

        return deleteById(entity.id)
    }

    override fun deleteAll() {
        logger.debug { "Borrando todos los ingredientes" }

        val sql = """
            DELETE FROM ingredientes
        """.trimIndent()

        DataBaseService.db.use {
            it.prepareStatement(sql).use { stm ->
                stm.executeUpdate()
            }
        }
    }

    override fun saveAll(entities: Iterable<Ingrediente>) {
        logger.debug { "Guardando todos los ingredientes" }

        entities.forEach {
            save(it)
        }
    }

    override fun count(): Long {
        logger.debug { "Contando todos los ingredientes" }

        return findAll().count().toLong()
    }
}