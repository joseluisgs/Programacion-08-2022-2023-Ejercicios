package services.database

import config.ConfigApp
import models.Conductor
import services.database.base.IDataBaseManagerDML
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.util.*

private val logger = mu.KotlinLogging.logger { }

class DataBaseManagerConductorDML : IDataBaseManagerDML<Conductor> {

    private fun db(): Connection {
        return DriverManager.getConnection(ConfigApp.getUrlDbConection())
    }

    // Ejecutador de consulta general, que nos llega del repositorio
    override fun executeQuery(querySql: String, whereQuery: Any?): List<Conductor> {
        logger.debug { "DataManager: Cargando Consulta" }

        val items = mutableListOf<Conductor>()

        db().use {
            it.prepareStatement(querySql).use { stm ->

                // Pasamos los parámetros en caso de búsquedas personalizadas
                if (whereQuery != null) {
                    stm.setString(1, whereQuery.toString())
                }

                val resulSet = stm.executeQuery()
                while (resulSet.next()) {

                    items.add(
                        Conductor(
                            UUID.fromString(resulSet.getString("uuid")),
                            resulSet.getString("name")
                        )
                    )
                }
            }
        }
        return items
    }


    override fun deleteAllRecord(): Boolean {
        logger.debug { "DataManager: Borrado completo de registros" }

        var res = 0

        val sql = "DELETE FROM ConductorTable "
        db().use {
            it.prepareStatement(sql).use { stm ->
                res = stm.executeUpdate()
            }
        }
        return res == 1
    }

    override fun insertRecord(newEntity: Conductor): Conductor {
        logger.debug { "DataManager: Insertando/Creando ${newEntity::class.simpleName}" }

        val sql = """
            INSERT INTO ${newEntity::class.simpleName}Table (uuid,name)
            VALUES (?,?)
        """.trimIndent()

        db().use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.uuid.toString())
                stm.setString(2, newEntity.name)
                stm.executeUpdate()
            }
        }

        return newEntity.copy()
    }

    override fun updateRecord(newEntity: Conductor): Conductor {
        logger.debug { "DataManager: Actualizando ${newEntity::class.simpleName}" }

        val sql = """
            UPDATE ${newEntity::class.simpleName}Table 
            SET uuid = ?, name = ?
            WHERE uuid = ?
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.uuid.toString())
                stm.setString(2, newEntity.name)
                // El uuid al final porque en la consulta, el WHERE sería el último parámetro
                stm.setString(3, newEntity.uuid.toString())
                stm.executeUpdate()
            }
        }
        return newEntity.copy()
    }

    // Hay que tener en cuenta primero BORRAR a donde esté asociada su foreign_key
    override fun deleteRecord(entityToDelete: Conductor): Boolean {
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
}