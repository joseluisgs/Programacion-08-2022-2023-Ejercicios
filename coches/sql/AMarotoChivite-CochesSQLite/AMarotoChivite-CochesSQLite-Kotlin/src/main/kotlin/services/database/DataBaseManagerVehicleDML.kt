package services.database

import config.ConfigApp
import models.Vehicle
import services.database.base.IDataBaseManagerDML
import utils.getTypeMotor
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.util.*

private val logger = mu.KotlinLogging.logger { }

class DataBaseManagerVehicleDML : IDataBaseManagerDML<Vehicle> {

    private fun db(): Connection {
        return DriverManager.getConnection(ConfigApp.getUrlDbConection())
    }

    // Ejecutador de consulta general, que nos llega del repositorio
    override fun executeQuery(querySql: String, whereQuery: Any?): List<Vehicle> {
        logger.debug { "DataManager: Cargando Consulta" }

        val items = mutableListOf<Vehicle>()

        db().use {
            it.prepareStatement(querySql).use { stm ->

                // Pasamos los parámetros en caso de búsquedas personalizadas
                if (whereQuery != null) {
                    stm.setString(1, whereQuery.toString())
                }

                val resulSet = stm.executeQuery()
                while (resulSet.next()) {

                    // Diferenciamos los tipos de motor
                    val typeMotoString = resulSet.getString("type_motor")
                    var finalTypeMotor: Vehicle.TypeMotor = Vehicle.TypeMotor.NO_ASIGNED
                    finalTypeMotor = getTypeMotor(typeMotoString)

                    items.add(
                        Vehicle(
                            UUID.fromString(resulSet.getString("uuid")),
                            resulSet.getString("model"),
                            finalTypeMotor,
                            UUID.fromString(resulSet.getString("fk_conductor"))
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

        val sql = "DELETE FROM VehicleTable "
        db().use {
            it.prepareStatement(sql).use { stm ->
                res = stm.executeUpdate()
            }
        }
        return res == 1
    }

    override fun insertRecord(newEntity: Vehicle): Vehicle {
        logger.debug { "DataManager: Insertando/Creando ${newEntity::class.simpleName}" }

        val sql = """
            INSERT INTO ${newEntity::class.simpleName}Table 
            VALUES (?,?,?,?)
        """.trimIndent()

        db().use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.uuid.toString())
                stm.setString(2, newEntity.model)
                stm.setString(3, newEntity.motor.toString())
                stm.setString(4, newEntity.foreignUuidConductor.toString())
                stm.executeUpdate()
            }
        }

        return newEntity.copy()
    }

    override fun updateRecord(newEntity: Vehicle): Vehicle {
        logger.debug { "DataManager: Actualizando ${newEntity::class.simpleName}" }

        val sql = """
            UPDATE ${newEntity::class.simpleName}Table 
            SET uuid = ?, model = ?, type_motor = ? , fk_conductor = ?
            WHERE uuid = ?
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.uuid.toString())
                stm.setString(2, newEntity.model)
                stm.setString(3, newEntity.motor.toString())
                stm.setString(4, newEntity.foreignUuidConductor.toString())
                // El uuid al final porque en la consulta, el WHERE sería el último parámetro
                stm.setString(5, newEntity.uuid.toString())
                stm.executeUpdate()
            }
        }
        return newEntity.copy()
    }

    // Borramos cada Vehículo que corresponda con la clave foránea del conductor (así hemos determinado el enunciado)
    override fun deleteRecord(entityToDelete: Vehicle): Boolean {
        logger.debug { "DataManager: Borrando ${entityToDelete::class.simpleName}" }

        // Para verificar, si el statement me realiza la actualización
        var res = 0

        val sql = """
            DELETE FROM ${entityToDelete::class.simpleName}Table
            WHERE fk_conductor = ?
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, entityToDelete.foreignUuidConductor.toString())
                res = stm.executeUpdate()
            }
        }

        return res == 1
    }
}