package repositories

import models.Conductor
import models.Vehicle
import repositories.base.IRepositoryCrud
import services.database.DataBaseManagerConductorDML
import services.database.DataBaseManagerVehicleDML
import java.util.*

private val logger = mu.KotlinLogging.logger { }

class ConductorRepository : IRepositoryCrud<Conductor> {
    // Cargamos el Manager para el repositorio
    private val managerDML = DataBaseManagerConductorDML()

    // Cargo el Manager de los Vehículos (aunque hagamos un poco de acoplamiento)
    private val managerVehicleDML = DataBaseManagerVehicleDML()

    override fun getAll(): List<Conductor> {
        logger.debug { "Repository: Obtener todos los items" }

        val sqlQuery = """SELECT * FROM ConductorTable""".trimIndent()
        return managerDML.executeQuery(sqlQuery, null)
    }

    override fun getById(item: Conductor): Conductor? {
        logger.debug { "Repository: Obtener el item por ID" }

        val sqlQuery = """SELECT * FROM ConductorTable WHERE uuid = ?""".trimIndent()

        return if (managerDML.executeQuery(sqlQuery, item.uuid.toString()).isEmpty()) {
            null
        } else {
            managerDML.executeQuery(sqlQuery, item.uuid.toString())[0]
        }
    }

    override fun saveItem(item: Conductor): Conductor {
        return if (existItem(item)) {
            logger.debug { "Repository: Existe $item y actualizamos" }

            managerDML.updateRecord(item)
        } else {
            logger.debug { "Repository: No existe $item e insertamos" }

            managerDML.insertRecord(item)
        }
    }

    override fun deleteItem(item: Conductor): Boolean {
        logger.debug { "Repository: Borrando $item" }

        // Primero debemos borrar los coches
        // Creo un coche ficticio con la foreign_key del conductor para poder eliminarlo, ya que solo necesitamos la FK
        val falseVehicle = Vehicle(UUID.randomUUID(), "x", Vehicle.TypeMotor.NO_ASIGNED, item.uuid)

        // Borramos todos los vehículos que tenga el conductor
        managerVehicleDML.deleteRecord(falseVehicle)

        // Borramos el conductor
        return managerDML.deleteRecord(item)
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Repository: Borrando todos los items" }

        // Borramos todos los vehículos
        managerVehicleDML.deleteAllRecord()

        // Borramos todos los conductores
        managerDML.deleteAllRecord()

        return getAll().isEmpty()
    }

    override fun existItem(item: Conductor): Boolean {
        val sqlQuery = """
            SELECT * 
            FROM ConductorTable
            WHERE uuid = ?
            """.trimIndent()
        val listItem = managerDML.executeQuery(sqlQuery, item.uuid)

        return listItem.isNotEmpty()
    }
}