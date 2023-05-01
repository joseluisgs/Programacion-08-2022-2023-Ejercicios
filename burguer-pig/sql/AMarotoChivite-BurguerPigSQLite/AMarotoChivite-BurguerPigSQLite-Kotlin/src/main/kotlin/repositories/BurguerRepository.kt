package repositories

import models.Burguer
import repositories.base.IRepositoryCrud
import services.database.DataBaseManagerBurguerDML

private val logger = mu.KotlinLogging.logger { }

class BurguerRepository : IRepositoryCrud<Burguer> {
    // Cargamos el Manager para el repositorio
    private val managerDML = DataBaseManagerBurguerDML()

    override fun getAll(): List<Burguer> {
        logger.debug { "Repository: Obtener todos los items" }

        val sqlQuery = """SELECT * FROM BurguerTable""".trimIndent()
        val sqlQueryLineas = """SELECT * FROM LineaBurguerTable""".trimIndent()
        return managerDML.executeQuery(sqlQuery, sqlQueryLineas,null,null)
    }

    override fun getById(item: Burguer): Burguer? {
        logger.debug { "Repository: Obtener el item por ID" }

        val sqlQuery = """SELECT * FROM BurguerTable WHERE uuid = ?""".trimIndent()
        val sqlQueryLinea = """SELECT * FROM LineaBurguerTable WHERE burguer_uuid = ?""".trimIndent()

        return if (managerDML.executeQuery(sqlQuery,sqlQueryLinea,item.uuid, item.uuid).isEmpty()) {
            null
        } else {
            managerDML.executeQuery(sqlQuery,sqlQueryLinea,item.uuid, item.uuid)[0]
        }
    }

    override fun saveItem(item: Burguer): Burguer {
        return if (existItem(item)) {
            logger.debug { "Repository: Existe ${item::class.simpleName} y actualizamos" }

            managerDML.updateRecord(item)
        } else {
            logger.debug { "Repository: No existe ${item::class.simpleName} e insertamos" }

            managerDML.insertRecord(item)
        }
    }

    override fun deleteItem(item: Burguer): Boolean {
        logger.debug { "Repository: Borrando ${item::class.simpleName}" }

        return managerDML.deleteRecord(item)
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Repository: Borrando todos los items" }

        // Borramos todos los vehículos
        managerDML.deleteAllRecord()

        return getAll().isEmpty()
    }

    override fun existItem(item: Burguer): Boolean {
        val sqlQuery = """
            SELECT * 
            FROM BurguerTable
            WHERE uuid = ?
            """.trimIndent()
        val listItem = managerDML.executeQuery(sqlQuery, null,item.uuid,null)

        return listItem.isNotEmpty()
    }
}