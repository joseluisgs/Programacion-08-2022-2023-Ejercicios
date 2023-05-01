package repositories

import mappers.toDocencia
import models.Docencia
import repositories.base.IRepositoryCrud
import services.database.DocenciaSqlDelightClient

private val logger = mu.KotlinLogging.logger { }

class DocenciaRepository : IRepositoryCrud<Docencia> {
    // Cargamos el cliente
    private val db = DocenciaSqlDelightClient.docenciaQueries

    override fun getAll(): List<Docencia> {
        logger.debug { "${this::class.simpleName}: Obtener todos los items" }

        return db.getAll().executeAsList().map { toDocencia(it) }
    }

    override fun getById(item: Docencia): Docencia? {
        logger.debug { "${this::class.simpleName}: Obtener el item por ID" }

        val result = db.getById(item.idProfesor,item.uuidModulo.toString()).executeAsOneOrNull()

        return if (result != null) {
            toDocencia(result)
        }else {
            null
        }
    }

    override fun saveItem(item: Docencia): Docencia {

        if (existItem(item)){
            logger.debug { "${this::class.simpleName}: Modificando ${item::class.simpleName}" }

            // Update
            db.transaction {
                db.update(
                    item.curso.toLong(),
                    item.grado.toString(),
                    item.idProfesor,
                    item.uuidModulo.toString()
                )
            }
            return toDocencia(db.getById(item.idProfesor,item.uuidModulo.toString()).executeAsOne())
        }else{
            logger.debug { "${this::class.simpleName}: Insertando ${item::class.simpleName}" }

            // Insert
            db.transaction {
                db.insert(
                    item.idProfesor,
                    item.uuidModulo.toString(),
                    item.curso.toLong(),
                    item.grado.toString(),
                )
            }
            return toDocencia(db.getById(item.idProfesor,item.uuidModulo.toString()).executeAsOne())
        }
    }

    override fun deleteItem(item: Docencia): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando ${item::class.simpleName}" }

        db.delete(item.idProfesor,item.uuidModulo.toString())
        return true
    }

    override fun deleteAll(): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando todos los items" }

        db.removeAll()

        return getAll().isEmpty()
    }

    override fun existItem(item: Docencia): Boolean {
        return getById(item) != null
    }
}