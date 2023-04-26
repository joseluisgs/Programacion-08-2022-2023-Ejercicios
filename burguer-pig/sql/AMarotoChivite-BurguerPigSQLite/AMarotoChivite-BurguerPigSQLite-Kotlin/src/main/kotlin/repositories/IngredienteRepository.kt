package repositories

import models.Ingrediente
import repositories.base.IRepositoryCrud
import services.database.DataBaseManagerIngredientDML

private val logger = mu.KotlinLogging.logger { }

class IngredienteRepository : IRepositoryCrud<Ingrediente> {
    // Cargamos el Manager para el repositorio
    private val managerDML = DataBaseManagerIngredientDML()

    override fun getAll(): List<Ingrediente> {
        logger.debug { "Repository: Obtener todos los items" }

        val sqlQuery = """SELECT * FROM IngredienteTable""".trimIndent()
        return managerDML.executeQuery(sqlQuery, null, null, null)
    }

    override fun getById(item: Ingrediente): Ingrediente? {
        logger.debug { "Repository: Obtener el item por ID" }

        val sqlQuery = """SELECT * FROM IngredienteTable WHERE id = ?""".trimIndent()

        return if (managerDML.executeQuery(sqlQuery, null, item.id, null).isEmpty()) {
            null
        } else {
            managerDML.executeQuery(sqlQuery, null, item.id, null)[0]
        }
    }

    override fun saveItem(item: Ingrediente): Ingrediente {
        return if (existItem(item)) {
            logger.debug { "Repository: Existe ${item::class.simpleName} y actualizamos" }

            managerDML.updateRecord(item)
        } else {
            logger.debug { "Repository: No existe ${item::class.simpleName} e insertamos" }

            managerDML.insertRecord(item)
        }
    }

    override fun deleteItem(item: Ingrediente): Boolean {
        logger.debug { "Repository: Borrando ${item::class.simpleName}" }

        return managerDML.deleteRecord(item)
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Repository: Borrando todos los items" }

        managerDML.deleteAllRecord()

        return getAll().isEmpty()
    }

    override fun existItem(item: Ingrediente): Boolean {
        val sqlQuery = """
            SELECT * 
            FROM IngredienteTable
            WHERE id = ?
            """.trimIndent()
        val listItem = managerDML.executeQuery(sqlQuery, null, item.id, null)

        return listItem.isNotEmpty()
    }
}