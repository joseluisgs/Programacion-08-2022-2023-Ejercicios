package services.database

import config.ConfigApp
import models.Docencia
import services.database.base.IDataBaseManagerDML
import utils.typeGrade
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

private val logger = mu.KotlinLogging.logger { }

class DataBaseManagerDocenciaDML : IDataBaseManagerDML<Docencia> {

    private fun db(): Connection {
        return DriverManager.getConnection(ConfigApp.getUrlDbConection())
    }

    override fun executeQuery(querySql: String, whereQuery: Any?, whereQuery2: Any?): List<Docencia> {
        logger.debug { "DataManager: Cargando Consulta" }

        val items = mutableListOf<Docencia>()

        db().use {
            it.prepareStatement(querySql).use { stm ->

                // Pasamos los parámetros en caso de búsquedas personalizadas
                if (whereQuery != null) {
                    stm.setLong(1, whereQuery as Long)
                }

                if (whereQuery2 != null) {
                    stm.setString(1, whereQuery2.toString())
                }

                val resulSet = stm.executeQuery()
                while (resulSet.next()) {

                    items.add(
                        Docencia(
                            resulSet.getLong("id_profesor"),
                            UUID.fromString(resulSet.getString("uuid_modulo")),
                            resulSet.getInt("curso"),
                            typeGrade(resulSet.getString("grado"))
                        )
                    )
                }
            }
        }
        return items
    }

    override fun insertRecord(newEntity: Docencia): Docencia {
        logger.debug { "DataManager: Insertando/Creando ${newEntity::class.simpleName}" }

        val sql = """
            INSERT INTO ${newEntity::class.simpleName}Table 
            VALUES (?,?,?,?)
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setLong(1, newEntity.idProfesor)
                stm.setString(2, newEntity.uuidModulo.toString())
                stm.setInt(3, newEntity.curso)
                stm.setString(4, newEntity.grado.toString())
                stm.executeUpdate()
            }
        }
        return newEntity.copy()
    }

    override fun updateRecord(newEntity: Docencia): Docencia {
        logger.debug { "DataManager: Actualizando ${newEntity::class.simpleName}" }

        val sql = """
            UPDATE ${newEntity::class.simpleName}Table 
            SET id_profesor = ?, uuid_modulo = ?, curso = ?, grado = ?
            WHERE id_profesor = ? AND uuid_modulo = ? 
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setLong(1, newEntity.idProfesor)
                stm.setString(2, newEntity.uuidModulo.toString())
                stm.setInt(3, newEntity.curso)
                stm.setString(4, newEntity.grado.toString())
                stm.setLong(5, newEntity.idProfesor)
                stm.setString(6, newEntity.uuidModulo.toString())
                stm.executeUpdate()
            }
        }
        return newEntity.copy()
    }

    override fun deleteRecord(entityToDelete: Docencia): Boolean {
        logger.debug { "DataManager: Borrando ${entityToDelete::class.simpleName}" }

        // Para verificar, si el statement me realiza la actualización
        var res = 0

        val sql = """
            DELETE FROM ${entityToDelete::class.simpleName}Table
            WHERE id_profesor = ? AND uuid_modulo = ? 
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setLong(1, entityToDelete.idProfesor)
                stm.setString(2, entityToDelete.uuidModulo.toString())
                res = stm.executeUpdate()
            }
        }

        return res == 1
    }

    override fun deleteAllRecord(): Boolean {
        logger.debug { "DataManager: Borrado completo de registros" }

        var res = 0

        val sql = "DELETE FROM DocenciaTable "
        db().use {
            it.prepareStatement(sql).use { stm ->
                res = stm.executeUpdate()
            }
        }

        return res == 1
    }
}