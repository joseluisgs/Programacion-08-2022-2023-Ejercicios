package repositories

import models.Modulo
import repositories.base.IRepositoryCrud
import services.database.DataBaseManagerModuloDML

private val logger = mu.KotlinLogging.logger { }

class ModuloRepository : IRepositoryCrud<Modulo> {
    // Cargamos el Manager para el repositorio
    private val managerDML = DataBaseManagerModuloDML()

    override fun getAll(): List<Modulo> {
        logger.debug { "Repository: Obtener todos los items" }

        val sqlQuery = """SELECT * FROM ModuloTable""".trimIndent()
        return managerDML.executeQuery(sqlQuery, null, null)
    }

    override fun getById(item: Modulo): Modulo? {
        logger.debug { "Repository: Obtener el item por ID" }

        val sqlQuery = """SELECT * FROM ModuloTable WHERE uuid = ?""".trimIndent()

        return if (managerDML.executeQuery(sqlQuery, item.uuid, null).isEmpty()) {
            null
        } else {
            managerDML.executeQuery(sqlQuery, item.uuid, null)[0]
        }
    }

    override fun saveItem(item: Modulo): Modulo {
        return if (existItem(item)) {
            logger.debug { "Repository: Existe ${item::class.simpleName} y actualizamos" }

            managerDML.updateRecord(item)
        } else {
            logger.debug { "Repository: No existe ${item::class.simpleName} e insertamos" }

            managerDML.insertRecord(item)
        }
    }

    override fun deleteItem(item: Modulo): Boolean {
        logger.debug { "Repository: Borrando ${item::class.simpleName}" }

        return managerDML.deleteRecord(item)
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Repository: Borrando todos los items" }

        managerDML.deleteAllRecord()

        return getAll().isEmpty()
    }

    override fun existItem(item: Modulo): Boolean {
        val sqlQuery = """
            SELECT * 
            FROM ModuloTable
            WHERE uuid = ?
            """.trimIndent()
        val listItem = managerDML.executeQuery(sqlQuery, item.uuid, null)

        return listItem.isNotEmpty()
    }
}