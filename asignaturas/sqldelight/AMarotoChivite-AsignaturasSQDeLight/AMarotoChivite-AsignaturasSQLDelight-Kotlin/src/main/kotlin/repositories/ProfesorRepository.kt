package repositories

import mappers.toProfesor
import models.Profesor
import repositories.base.IRepositoryCrud
import services.database.ProfesorSqlDelightClient

private val logger = mu.KotlinLogging.logger { }

class ProfesorRepository : IRepositoryCrud<Profesor> {
    // Cargamos el cliente
    private val db = ProfesorSqlDelightClient.profesorQueries

    override fun getAll(): List<Profesor> {
        logger.debug { "${this::class.simpleName}: Obtener todos los items" }

        return db.getAll().executeAsList().map { toProfesor(it) }
    }

    override fun getById(item: Profesor): Profesor? {
        logger.debug { "${this::class.simpleName}: Obtener el item por ID" }

        val result = db.getById(item.id).executeAsOneOrNull()

        return if (result != null) {
           toProfesor(result)
        }else {
            null
        }
    }

    override fun saveItem(item: Profesor): Profesor {

        if (existItem(item)){
            logger.debug { "${this::class.simpleName}: Modificando ${item::class.simpleName}" }

            // Update
            db.transaction {
                db.update(
                    item.name,
                    item.dateInit.toString(),
                    item.id
                )
            }
            return toProfesor(db.getById(item.id).executeAsOne())
        }else{
            logger.debug { "${this::class.simpleName}: Insertando ${item::class.simpleName}" }

            // Insert
            db.transaction {
                db.insert(
                    item.id,
                    item.name,
                    item.dateInit.toString()
                )
            }
            return toProfesor(db.getById(item.id).executeAsOne())
        }
    }

    override fun deleteItem(item: Profesor): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando ${item::class.simpleName}" }

        db.delete(item.id)
        return true
    }

    override fun deleteAll(): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando todos los items" }

        db.removeAll()

        return getAll().isEmpty()
    }

    override fun existItem(item: Profesor): Boolean {
        return getById(item) != null
    }
}