package repositories

import models.Vehicle
import repositories.base.IRepositoryCrud
import services.database.DataBaseManagerVehicleDML

private val logger = mu.KotlinLogging.logger { }

class VehicleRepository : IRepositoryCrud<Vehicle> {
    // Cargamos el Manager para el repositorio
    private val managerDML = DataBaseManagerVehicleDML()

    override fun getAll(): List<Vehicle> {
        logger.debug { "Repository: Obtener todos los items" }

        val sqlQuery = """SELECT * FROM VehicleTable""".trimIndent()
        return managerDML.executeQuery(sqlQuery, null)
    }


    override fun getById(item: Vehicle): Vehicle? {
        logger.debug { "Repository: Obtener el item por ID" }

        val sqlQuery = """SELECT * FROM VehicleTable WHERE uuid = ?""".trimIndent()

        return if (managerDML.executeQuery(sqlQuery, item.uuid.toString()).isEmpty()) {
            null
        } else {
            managerDML.executeQuery(sqlQuery, item.uuid.toString())[0]
        }
    }

    override fun saveItem(item: Vehicle): Vehicle {
        return if (existItem(item)) {
            logger.debug { "Repository: Existe $item y actualizamos" }

            managerDML.updateRecord(item)
        } else {
            logger.debug { "Repository: No existe $item e insertamos" }

            managerDML.insertRecord(item)
        }
    }

    override fun deleteItem(item: Vehicle): Boolean {
        logger.debug { "Repository: Borrando $item" }

        return managerDML.deleteRecord(item)
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Repository: Borrando todos los items" }

        managerDML.deleteAllRecord()

        return getAll().isEmpty()
    }

    override fun existItem(item: Vehicle): Boolean {
        val sqlQuery = """
            SELECT * 
            FROM VehicleTable
            WHERE uuid = ?
            """.trimIndent()
        val listItem = managerDML.executeQuery(sqlQuery, item.uuid.toString())

        return listItem.isNotEmpty()
    }
}