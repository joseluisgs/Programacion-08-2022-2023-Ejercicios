package repositories

import models.Docencia
import repositories.base.IRepositoryCrud
import services.database.DataBaseManagerDocenciaDML

private val logger = mu.KotlinLogging.logger { }

class DocenciaRepository : IRepositoryCrud<Docencia> {
    // Cargamos el Manager para el repositorio
    private val managerDML = DataBaseManagerDocenciaDML()

    override fun getAll(): List<Docencia> {
        logger.debug { "Repository: Obtener todos los items" }

        val sqlQuery = """SELECT * FROM DocenciaTable""".trimIndent()
        return managerDML.executeQuery(sqlQuery, null, null)
    }

    override fun getById(item: Docencia): Docencia? {
        logger.debug { "Repository: Obtener el item por ID" }

        val sqlQuery = """SELECT * FROM DocenciaTable WHERE id_profesor = ? AND uuid_modulo = ?""".trimIndent()

        return if (managerDML.executeQuery(sqlQuery, item.idProfesor, item.uuidModulo).isEmpty()) {
            null
        } else {
            managerDML.executeQuery(sqlQuery, item.idProfesor, item.uuidModulo)[0]
        }
    }

    override fun saveItem(item: Docencia): Docencia {
        return if (existItem(item)) {
            logger.debug { "Repository: Existe ${item::class.simpleName} y actualizamos" }

            managerDML.updateRecord(item)
        } else {
            logger.debug { "Repository: No existe ${item::class.simpleName} e insertamos" }

            managerDML.insertRecord(item)
        }
    }

    override fun deleteItem(item: Docencia): Boolean {
        logger.debug { "Repository: Borrando ${item::class.simpleName}" }

        return managerDML.deleteRecord(item)
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Repository: Borrando todos los items" }

        managerDML.deleteAllRecord()

        return getAll().isEmpty()
    }

    override fun existItem(item: Docencia): Boolean {
        val sqlQuery = """
            SELECT * 
            FROM DocenciaTable
            WHERE id_profesor = ? AND uuid_modulo = ?
            """.trimIndent()
        val listItem = managerDML.executeQuery(sqlQuery, item.idProfesor, item.uuidModulo)

        return listItem.isNotEmpty()
    }
}