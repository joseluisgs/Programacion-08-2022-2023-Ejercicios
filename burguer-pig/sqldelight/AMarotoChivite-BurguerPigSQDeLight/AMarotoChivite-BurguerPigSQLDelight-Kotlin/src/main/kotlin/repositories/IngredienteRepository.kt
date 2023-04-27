package repositories

import mappers.toIngredient
import models.Ingrediente
import repositories.base.IRepositoryCrud
import services.database.IngredienteSqlDelightClient


private val logger = mu.KotlinLogging.logger { }

class IngredienteRepository : IRepositoryCrud<Ingrediente> {
    // Cargamos el cliente
    private val db = IngredienteSqlDelightClient.ingredienteQueries

    override fun getAllBurguer(): List<Ingrediente> {
        logger.debug { "${this::class.simpleName}: Obtener todos los items" }

        return db.getAll().executeAsList().map { toIngredient(it) }
    }

    override fun getById(item: Ingrediente): Ingrediente? {
        logger.debug { "${this::class.simpleName}: Obtener el item por ID" }

        val result = db.getById(item.id).executeAsOneOrNull()
        return if (result != null) {
            toIngredient(result)
        }else {
            null
        }
    }

    override fun saveItem(item: Ingrediente): Ingrediente {
        if (existItem(item)){
            var setBooleanToInt = 1L
            if (!item.avaliable){
                setBooleanToInt = 0L
            }

            // Update
            db.transaction {
                db.update(
                    item.name,
                    item.price,
                    item.stock.toLong(),
                    item.createdAt.toString(),
                    item.updatedAt.toString(),
                    setBooleanToInt,
                    item.id
                )
            }
            return toIngredient(db.getById(item.id).executeAsOne())
        }else{
            var setBooleanToInt = 1L
            if (!item.avaliable){
                setBooleanToInt = 0L
            }

            // Insert
            db.transaction {
                db.insert(
                    item.id,
                    item.name,
                    item.price,
                    item.stock.toLong(),
                    item.createdAt.toString(),
                    item.updatedAt.toString(),
                    setBooleanToInt
                )
            }
            return toIngredient(db.getById(item.id).executeAsOne())
        }

    }

    override fun deleteItem(item: Ingrediente): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando ${item::class.simpleName}" }

        db.delete(item.id)
        return true
    }

    override fun deleteAll(): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando todos los items" }

        db.removeAll()

        return getAllBurguer().isEmpty()
    }

    override fun existItem(item: Ingrediente): Boolean {

        return getById(item) != null
    }
}