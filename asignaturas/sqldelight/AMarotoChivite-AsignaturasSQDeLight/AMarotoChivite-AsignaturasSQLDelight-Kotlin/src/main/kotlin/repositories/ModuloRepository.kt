package repositories

import mappers.toModulo
import models.Modulo
import repositories.base.IRepositoryCrud
import services.database.ModuloSqlDelightClient

private val logger = mu.KotlinLogging.logger { }

class ModuloRepository : IRepositoryCrud<Modulo> {
    // Cargamos el cliente
    private val db = ModuloSqlDelightClient.moduloQueries

    override fun getAll(): List<Modulo> {
        logger.debug { "${this::class.simpleName}: Obtener todos los items" }

        return db.getAll().executeAsList().map { toModulo(it) }
    }

    override fun getById(item: Modulo): Modulo? {
        logger.debug { "${this::class.simpleName}: Obtener el item por ID" }

        val result = db.getById(item.uuid.toString()).executeAsOneOrNull()

        return if (result != null) {
            toModulo(result)
        }else {
            null
        }
    }

    override fun saveItem(item: Modulo): Modulo {

        if (existItem(item)){
            logger.debug { "${this::class.simpleName}: Modificando ${item::class.simpleName}" }

            // Update
            db.transaction {
                db.update(
                    item.name,
                    item.curso.toLong(),
                    item.grado.toString(),
                    item.uuid.toString()
                )
            }
            return toModulo(db.getById(item.uuid.toString()).executeAsOne())
        }else{
            logger.debug { "${this::class.simpleName}: Insertando ${item::class.simpleName}" }

            // Insert
            db.transaction {
                db.insert(
                    item.uuid.toString(),
                    item.name,
                    item.curso.toLong(),
                    item.grado.toString()
                )
            }
            return toModulo(db.getById(item.uuid.toString()).executeAsOne())
        }
    }

    override fun deleteItem(item: Modulo): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando ${item::class.simpleName}" }

        db.delete(item.uuid.toString())
        return true
    }

    override fun deleteAll(): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando todos los items" }

        db.removeAll()

        return getAll().isEmpty()
    }

    override fun existItem(item: Modulo): Boolean {
        return getById(item) != null
    }
}