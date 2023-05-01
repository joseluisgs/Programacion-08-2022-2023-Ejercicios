package repositories

import mappers.toBurguer
import mappers.toIngredient
import mappers.toLineaBurguer
import models.Burguer
import models.LineaBurguer
import repositories.base.IRepositoryCrud
import repositories.base.IRepositoryCrudToLineas
import services.database.BurguerSqlDelightClient

private val logger = mu.KotlinLogging.logger { }

class BurguerRepository : IRepositoryCrudToLineas<Burguer> {
    // Cargamos el cliente
    private val db = BurguerSqlDelightClient.burguerQueries

    override fun getAllBurguer(): List<Burguer> {
        logger.debug { "${this::class.simpleName}: Obtener todos los items" }

        // Añadimos a cada item sus Lineas
        val burguers = db.getAllBurguer().executeAsList().map { toBurguer(it) }
        val lineas = getAllLineaBurguer()

        burguers.forEach{ burguer ->
            burguer.lineaBurguer.addAll(lineas.filter { burguer.uuid == it.burguerUUID })
        }

        return burguers
    }

    override fun getAllLineaBurguer(): List<LineaBurguer> {
        logger.debug { "${this::class.simpleName}: Obtener todos los items" }

        return db.getAllLineasBurguer().executeAsList().map { toLineaBurguer(it) }
    }

    override fun getById(item: Burguer): Burguer? {
        logger.debug { "${this::class.simpleName}: Obtener el item por ID" }

        // Añadimos al item su Lineas
        val result = db.getByIdBurguer(item.uuid.toString()).executeAsOneOrNull()

        return if (result != null) {
            val burguer = toBurguer(result)
            val lineas = getAllLineaBurguer()

            lineas.forEach{
                if (it.burguerUUID == burguer.uuid){
                    burguer.lineaBurguer.add(it)
                }
            }

            burguer
        }else {
            null
        }
    }

    // Cuando guardemos una burguer tenemos que guardar las lineas en su tabla correspondiente
    override fun saveItem(item: Burguer): Burguer {

        if (existItem(item)){
            logger.debug { "${this::class.simpleName}: Modificando ${item::class.simpleName}" }

            // Update
            db.transaction {
                db.updateBurguer(
                    item.name,
                    item.stock.toLong(),
                    item.uuid.toString()
                    )
            }
            item.getLineas().forEach{
                db.transaction {
                    db.updateLineaBurguer(
                        it.burguerUUID.toString(),
                        it.ingredienteId.toString(),
                        it.ingredienteQuantity.toLong(),
                        it.ingredientePrice.toLong(),
                        it.lineaId
                    )
                }
            }
            return toBurguer(db.getByIdBurguer(item.uuid.toString()).executeAsOne())
        }else{
            logger.debug { "${this::class.simpleName}: Insertando ${item::class.simpleName}" }

            // Insert
            db.transaction {
                db.insertBurguer(
                    item.uuid.toString(),
                    item.name,
                    item.stock.toLong()
                )
            }
            item.getLineas().forEach{
                db.transaction {
                    db.insertLineaBurguer(
                        it.lineaId,
                        it.burguerUUID.toString(),
                        it.ingredienteId.toString(),
                        it.ingredienteQuantity.toLong(),
                        it.ingredientePrice.toLong(),
                    )
                }
            }
            return toBurguer(db.getByIdBurguer(item.uuid.toString()).executeAsOne())
        }
    }

    // Cuando eliminemos una burguer tenemos que eliminar las lineas en su tabla correspondiente
    override fun deleteItem(item: Burguer): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando ${item::class.simpleName}" }

        // Primero eliminamos las Lineas
        db.deleteLineaBurguer(item.uuid.toString())

        db.deleteBurguer(item.uuid.toString())
        return true
    }

    override fun deleteAll(): Boolean {
        logger.debug { "${this::class.simpleName}: Borrando todos los items" }

        // Primero eliminamos las Lineas
        db.removeAllLineaBurguer()

        db.removeAllBurguer()

        return getAllBurguer().isEmpty()
    }

    override fun existItem(item: Burguer): Boolean {

        return getById(item) != null
    }
}