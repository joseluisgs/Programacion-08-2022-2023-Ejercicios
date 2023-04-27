package services.database

import config.ConfigApp
import models.Modulo
import services.database.base.IDataBaseManagerDML
import utils.typeGrade
import java.sql.Connection
import java.sql.DriverManager
import java.util.*


private val logger = mu.KotlinLogging.logger { }

class DataBaseManagerModuloDML : IDataBaseManagerDML<Modulo> {

    private fun db(): Connection {
        return DriverManager.getConnection(ConfigApp.getUrlDbConection())
    }

    override fun executeQuery(querySql: String, whereQuery: Any?, whereQuery2: Any?): List<Modulo> {
        logger.debug { "DataManager: Cargando Consulta" }

        val items = mutableListOf<Modulo>()

        db().use {
            it.prepareStatement(querySql).use { stm ->

                // Pasamos los parámetros en caso de búsquedas personalizadas
                if (whereQuery != null) {
                    stm.setString(1, whereQuery.toString())
                }
                
                if (whereQuery2 != null) {
                    stm.setString(2, whereQuery2.toString())
                }

                val resulSet = stm.executeQuery()
                while (resulSet.next()) {

                    items.add(
                        Modulo(
                            UUID.fromString(resulSet.getString("uuid")),
                            resulSet.getString("name"),
                            resulSet.getInt("curso"),
                            typeGrade(resulSet.getString("grado"))
                        )
                    )
                }
            }
        }
        return items
    }

    override fun insertRecord(newEntity: Modulo): Modulo {
        logger.debug { "DataManager: Insertando/Creando ${newEntity::class.simpleName}" }

        val sql = """
            INSERT INTO ${newEntity::class.simpleName}Table 
            VALUES (?,?,?,?)
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->

                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.uuid.toString())
                stm.setString(2, newEntity.name)
                stm.setInt(3, newEntity.curso)
                stm.setString(4, newEntity.grado.toString())

                stm.executeUpdate()
            }
        }
        return newEntity.copy()
    }

    override fun updateRecord(newEntity: Modulo): Modulo {
        logger.debug { "DataManager: Actualizando ${newEntity::class.simpleName}" }

        val sql = """
            UPDATE ${newEntity::class.simpleName}Table 
            SET uuid = ?, name = ?, curso = ?, grado = ?
            WHERE uuid = ?
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->

                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.uuid.toString())
                stm.setString(2, newEntity.name)
                stm.setInt(3, newEntity.curso)
                stm.setString(4, newEntity.grado.toString())

                stm.executeUpdate()
            }
        }
        return newEntity.copy()
    }

    override fun deleteRecord(entityToDelete: Modulo): Boolean {
        logger.debug { "DataManager: Borrando ${entityToDelete::class.simpleName}" }

        // Para verificar, si el statement me realiza la actualización
        var res = 0

        val sql = """
            DELETE FROM ${entityToDelete::class.simpleName}Table
            WHERE uuid = ?
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, entityToDelete.uuid.toString())
                res = stm.executeUpdate()
            }
        }

        return res == 1
    }

    override fun deleteAllRecord(): Boolean {
        logger.debug { "DataManager: Borrado completo de registros" }

        var res = 0

        val sql = "DELETE FROM ModuloTable "
        db().use {
            it.prepareStatement(sql).use { stm ->
                res = stm.executeUpdate()
            }
        }

        return res == 1
    }
}