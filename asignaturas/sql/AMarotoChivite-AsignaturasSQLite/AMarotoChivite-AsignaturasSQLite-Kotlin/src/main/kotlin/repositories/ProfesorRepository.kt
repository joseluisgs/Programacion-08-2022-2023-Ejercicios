package repositories

import models.Profesor
import repositories.base.IRepositoryCrud
import services.database.DataBaseManagerProfesorDML

private val logger = mu.KotlinLogging.logger { }

class ProfesorRepository : IRepositoryCrud<Profesor> {
    // Cargamos el Manager para el repositorio
    private val managerDML = DataBaseManagerProfesorDML()

    override fun getAll(): List<Profesor> {
        logger.debug { "Repository: Obtener todos los items" }

        val sqlQuery = """SELECT * FROM ProfesorTable""".trimIndent()
        return managerDML.executeQuery(sqlQuery, null, null)
    }

    override fun getById(item: Profesor): Profesor? {
        logger.debug { "Repository: Obtener el item por ID" }

        val sqlQuery = """SELECT * FROM ProfesorTable WHERE id = ?""".trimIndent()

        return if (managerDML.executeQuery(sqlQuery, item.id, null).isEmpty()) {
            null
        } else {
            managerDML.executeQuery(sqlQuery, item.id, null)[0]
        }
    }

    override fun saveItem(item: Profesor): Profesor {
        return if (existItem(item)) {
            logger.debug { "Repository: Existe ${item::class.simpleName} y actualizamos" }

            managerDML.updateRecord(item)
        } else {
            logger.debug { "Repository: No existe ${item::class.simpleName} e insertamos" }

            managerDML.insertRecord(item)
        }
    }

    override fun deleteItem(item: Profesor): Boolean {
        logger.debug { "Repository: Borrando ${item::class.simpleName}" }

        return managerDML.deleteRecord(item)
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Repository: Borrando todos los items" }

        // Borramos todos los vehículos
        managerDML.deleteAllRecord()

        return getAll().isEmpty()
    }

    override fun existItem(item: Profesor): Boolean {
        val sqlQuery = """
            SELECT * 
            FROM ProfesorTable
            WHERE id = ?
            """.trimIndent()
        val listItem = managerDML.executeQuery(sqlQuery, item.id, null)

        return listItem.isNotEmpty()
    }
}