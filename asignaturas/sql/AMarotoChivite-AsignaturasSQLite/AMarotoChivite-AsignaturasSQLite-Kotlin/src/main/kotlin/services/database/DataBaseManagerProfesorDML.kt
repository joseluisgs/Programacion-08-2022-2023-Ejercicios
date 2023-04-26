package services.database

import config.ConfigApp

import models.Profesor
import services.database.base.IDataBaseManagerDML
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.time.LocalDate

private val logger = mu.KotlinLogging.logger { }

class DataBaseManagerProfesorDML : IDataBaseManagerDML<Profesor> {

    private fun db(): Connection {
        return DriverManager.getConnection(ConfigApp.getUrlDbConection())
    }

    override fun executeQuery(querySql: String, whereQuery: Any?, whereQuery2: Any?): List<Profesor> {
        logger.debug { "DataManager: Cargando Consulta" }

        val items = mutableListOf<Profesor>()

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
                        Profesor(
                            resulSet.getLong("id"),
                            resulSet.getString("name"),
                            LocalDate.parse(resulSet.getString("date_init"))
                        )
                    )
                }
            }
        }
        return items
    }

    override fun insertRecord(newEntity: Profesor): Profesor {
        logger.debug { "DataManager: Insertando/Creando ${newEntity::class.simpleName}" }

        val sql = """
            INSERT INTO ${newEntity::class.simpleName}Table 
            VALUES (null,?,?)
        """.trimIndent()

        var idCopied: Long = 0

        db().use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->

                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.name)
                stm.setString(2, newEntity.dateInit.toString())
                stm.executeUpdate()

                val generatedKeys = stm.generatedKeys
                if (generatedKeys.next()) {
                    idCopied = generatedKeys.getLong(1)
                }
            }
        }
        return newEntity.copy(id = idCopied)
    }

    override fun updateRecord(newEntity: Profesor): Profesor {
        logger.debug { "DataManager: Actualizando ${newEntity::class.simpleName}" }

        val sql = """
            UPDATE ${newEntity::class.simpleName}Table 
            SET id = null, name = ?, date_init = ?
            WHERE id = ?
        """.trimIndent()

        var idCopied: Long = 0

        db().use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->

                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.name)
                stm.setString(2, newEntity.dateInit.toString())
                stm.executeUpdate()

                val claves = stm.generatedKeys
                if (claves.next()) {
                    idCopied = claves.getLong(1)
                }
            }
        }
        return newEntity.copy(id = idCopied)
    }

    override fun deleteRecord(entityToDelete: Profesor): Boolean {
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

        val sql = "DELETE FROM ProfesorTable "
        db().use {
            it.prepareStatement(sql).use { stm ->
                res = stm.executeUpdate()
            }
        }

        return res == 1
    }
}