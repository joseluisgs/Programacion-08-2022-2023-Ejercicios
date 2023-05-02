package repositories.hamburguesa

import models.Hamburguesa
import models.LineaHamburguesa
import mu.KotlinLogging
import repositories.ingredientes.IngredienteRepositoryImplement
import service.database.DataBaseService
import java.sql.Statement
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger{}

class HamburguesasRepositoryImplement: HamburguesaRepository {
    override fun findAll(): Iterable<Hamburguesa> {
        logger.debug { "Cargando las hamburguesas desde una base de datos" }
        val items = mutableListOf<Hamburguesa>()
        val sql = "SELECT * FROM hamburguesa"
        DataBaseService.db.use { stm ->
            stm.prepareStatement(sql).use { stm ->
                val rs = stm.executeQuery()
                while (rs.next()) {
                    items.add(
                        Hamburguesa(
                            id = rs.getLong("id"),
                            uuid = UUID.fromString(rs.getString("uuid")),
                            name = rs.getString("name"),
                            cantidad = rs.getInt("cantidad"),
                            createdAt = LocalDateTime.parse(rs.getString("createdAt")),
                            updatedAt = LocalDateTime.parse(rs.getString("updatedAt"))
                        )
                    )
                }
            }
        }
        if (items.isNotEmpty()){
            val sql = "SELECT * FROM Linea_Hamburguesa WHERE venta_id = ?"
            items.forEach{ item ->
                DataBaseService.db.use { stm ->
                    stm.prepareStatement(sql).use { stm ->
                        val rs = stm.executeQuery()
                        while (rs.next()) {
                            item.addLineaIngrediente(
                                LineaHamburguesa(
                                    lineaId = rs.getLong("linea_id"),
                                    idIngrediente = rs.getLong("id_ingrediente"),
                                    idHamburguesa = rs.getLong("id_hamburguesas"),
                                    precioIngrediente = rs.getDouble("precio_ingrediente"),
                                    cantidadIngrediente = rs.getInt("cantidad_ingrediente"),
                                )
                            )
                        }
                    }
                }

            }
        }
        return items
    }

    override fun findById(id: Long): Hamburguesa? {
        logger.debug { "Buscando ingrediente con el id: $id" }
        var item: Hamburguesa? = null
        val sql = "SELECT * FROM hamburguesa WHERE id =?"
        DataBaseService.db.use {
            it.prepareStatement(sql).use { stm ->
                stm.setLong(1, id)
                val rs = stm.executeQuery()
                rs?.let {
                    while (it.next()) {
                        item = Hamburguesa(
                            id = it.getLong("id"),
                            uuid = UUID.fromString(it.getString("uuid")),
                            name = it.getString("name"),
                            cantidad = it.getInt("cantidad"),
                            createdAt = LocalDateTime.parse(it.getString("createdAt")),
                            updatedAt = LocalDateTime.parse(it.getString("updatedAt"))
                        )
                    }
                }
            }
        }
        return item
    }

    override fun existsById(id: Long): Boolean {
        logger.debug { "Buscando si existe el hamburguesa con id: $id" }
        return findById(id) != null
    }

    override fun save(entity: Hamburguesa): Hamburguesa {
        logger.debug { "Guardando hamburguesa en la base de datos" }

        val ingredienteRepositoryImplement = IngredienteRepositoryImplement()
        // Revisar si existe la hamburguesa para no crear uno nuevo
        return if (ingredienteRepositoryImplement.existsById(entity.id)){
            update(entity)
        } else {
            create(entity)
        }
    }

    private fun create(entity: Hamburguesa): Hamburguesa {
        logger.debug { "Creando hamburguesa en la base de datos" }
        val createdTime = LocalDateTime.now()
        var newId: Long = 0

        val sql = """
        INSERT INTO hamburguesa VALUES (NULL,?,?,?,?,?,?)
        """.trimIndent()

        DataBaseService.db.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                stm.setString(1, entity.uuid.toString())
                stm.setString(2, entity.name)
                stm.setDouble(3, entity.precio)
                stm.setInt(4, entity.cantidad)
                stm.setString(5, createdTime.toString())
                stm.setString(6, createdTime.toString())

                stm.executeUpdate()

                val claves = stm.generatedKeys
                if(claves.next()){
                    newId = claves.getLong(1)
                }
            }
        }
        entity.lineaHamburguesa.forEach { linea ->
            val sql2 = "INSERT INTO Linea_Hamburguesa VALUES (?, ?, ?, ?, ?)"
            DataBaseService.db.use {
                it.prepareStatement(sql2).use { stm ->
                    stm.setLong(1, linea.lineaId)
                    stm.setLong(2, linea.idIngrediente)
                    stm.setLong(3, newId)
                    stm.setDouble(4, linea.precioIngrediente)
                    stm.setInt(5, linea.cantidadIngrediente)

                    stm.executeUpdate()
                }
            }
        }
        return entity.copy(
            id = newId,
            createdAt = createdTime,
            updatedAt = createdTime,
            lineaHamburguesa = entity.lineaHamburguesa.map { it.copy(idHamburguesa = newId) }.toMutableList()
        )
    }

    override fun update(entity: Hamburguesa): Hamburguesa {
        logger.debug { "Actualizando hamburguesas en la base de datos" }

        val updatedTime = LocalDateTime.now()

        val sql = """
        UPDATE hamburguesa SET name =?, cantidad =?, updatedAt =? WHERE id =?
        """.trimIndent()
        DataBaseService.db.use {
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, entity.name)
                stm.setInt(2, entity.cantidad)
                stm.setString(3, updatedTime.toString())
                stm.setLong(4, entity.id)

                stm.executeUpdate()
            }
        }
        val sql2 = "UPDATE Linea_Hamburguesa SET id_ingrediente =?, precio_ingrediente =?, cantidad_ingrediente =? WHERE lineaId =?"
        entity.lineaHamburguesa.forEach { linea ->
            DataBaseService.db.use {
                it.prepareStatement(sql2).use { stm ->
                    stm.setLong(1, linea.idIngrediente)
                    stm.setDouble(2, linea.precioIngrediente)
                    stm.setInt(3, linea.cantidadIngrediente)
                    stm.setLong(4, entity.id)

                    stm.executeUpdate()
                }
            }
        }
        return entity.copy(updatedAt = updatedTime)
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "Borrando hamburguesa con id $id" }

        var res = 0

        val sql = """
            DELETE FROM hamburguesa WHERE id =?
        """.trimIndent()
        DataBaseService.db.use {
            it.prepareStatement(sql).use { stm ->
                stm.setLong(1, id)

                res = stm.executeUpdate()
            }
        }
        return res == 1
    }

    override fun delete(entity: Hamburguesa): Boolean {
        logger.debug { "Borrando hamburguesa"}

        return deleteById(entity.id)
    }

    override fun deleteAll() {
        logger.debug("Borrando todas las hamburguesas")

        val sql = """
            DELETE FROM hamburguesa
        """.trimIndent()

        DataBaseService.db.use {
            it.prepareStatement(sql).use { stm ->
                stm.executeUpdate()
            }
        }
    }

    override fun saveAll(entities: Iterable<Hamburguesa>) {
        logger.debug { "Guardando todas las hamburguesas" }

        entities.forEach{
            save(it)
        }
    }

    override fun count(): Long {
        logger.debug { "Contando todas las hamburguesas" }

        return findAll().count().toLong()
    }
}