package services.database

import config.ConfigApp
import models.Ingrediente
import services.database.base.IDataBaseManagerDML
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.time.LocalDateTime

private val logger = mu.KotlinLogging.logger { }

class DataBaseManagerIngredientDML : IDataBaseManagerDML<Ingrediente> {

    private fun db(): Connection {
        return DriverManager.getConnection(ConfigApp.getUrlDbConection())
    }

    // Ejecutador de consulta general, que nos llega del repositorio
    override fun executeQuery(
        querySql: String,
        querySql2: String?,
        whereQuery: Any?,
        whereQuery2: Any?
    ): List<Ingrediente> {
        logger.debug { "DataManager: Cargando Consulta" }

        val items = mutableListOf<Ingrediente>()

        db().use {
            it.prepareStatement(querySql).use { stm ->

                // Pasamos los parámetros en caso de búsquedas personalizadas
                if (whereQuery != null && whereQuery is String) {
                    stm.setString(1, whereQuery.toString())
                }
                if (whereQuery != null && whereQuery is Long) {
                    stm.setLong(1, whereQuery.toLong())
                }
                if (whereQuery2 != null) {
                    stm.setString(1, whereQuery2.toString())
                }

                val resulSet = stm.executeQuery()
                while (resulSet.next()) {

                    items.add(
                        Ingrediente(
                            resulSet.getLong("id"),
                            resulSet.getString("name"),
                            resulSet.getDouble("price"),
                            resulSet.getInt("stock"),
                            LocalDateTime.parse(resulSet.getString("created_at")),
                            LocalDateTime.parse(resulSet.getString("updated_at")),
                            resulSet.getBoolean("avaliable")
                        )
                    )
                }
            }
        }
        return items
    }

    override fun insertRecord(newEntity: Ingrediente): Ingrediente {
        logger.debug { "DataManager: Insertando/Creando ${newEntity::class.simpleName}" }

        val createdTime = LocalDateTime.now()
        var copyId: Long = 0

        val sql = """
            INSERT INTO ${newEntity::class.simpleName}Table 
            VALUES (null,?,?,?,?,?,?)
        """.trimIndent()

        db().use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.name)
                stm.setDouble(2, newEntity.price)
                stm.setInt(3, newEntity.stock)
                stm.setString(4, createdTime.toString())
                stm.setString(5, createdTime.toString())
                stm.setBoolean(6, newEntity.avaliable)
                stm.executeUpdate()

                val claves = stm.generatedKeys
                if (claves.next()) {
                    copyId = claves.getLong(1)
                }
            }
        }

        return newEntity.copy(id = copyId, createdAt = createdTime, updatedAt = createdTime)
    }

    override fun updateRecord(newEntity: Ingrediente): Ingrediente {
        logger.debug { "DataManager: Actualizando ${newEntity::class.simpleName}" }

        val updatedTime = LocalDateTime.now()

        val sql = """
            UPDATE ${newEntity::class.simpleName}Table 
            SET id = null, name = ?, price = ? , stock = ?, update_at = ?, avaliable = ? 
            WHERE id = ?
        """.trimIndent()

        var idCopied: Long = 0

        db().use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.name)
                stm.setDouble(2, newEntity.price)
                stm.setInt(3, newEntity.stock)
                stm.setString(4, newEntity.updatedAt.toString())
                stm.setBoolean(5, newEntity.avaliable)
                stm.setLong(6, newEntity.id)
                stm.executeUpdate()

                val claves = stm.generatedKeys
                if (claves.next()) {
                    idCopied = claves.getLong(1)
                }
            }
        }
        return newEntity.copy(id = idCopied, updatedAt = updatedTime)
    }

    override fun deleteRecord(entityToDelete: Ingrediente): Boolean {
        logger.debug { "DataManager: Borrando ${entityToDelete::class.simpleName}" }

        // Para verificar, si el statement me realiza la actualización
        var res = 0

        val sql = """
            DELETE FROM ${entityToDelete::class.simpleName}Table
            WHERE id = ?
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setLong(1, entityToDelete.id)
                res = stm.executeUpdate()
            }
        }

        return res == 1
    }


    override fun deleteAllRecord(): Boolean {
        logger.debug { "DataManager: Borrado completo de registros" }

        var res = 0

        val sql = "DELETE FROM IngredienteTable "
        db().use {
            it.prepareStatement(sql).use { stm ->
                res = stm.executeUpdate()
            }
        }
        return res == 1
    }
}