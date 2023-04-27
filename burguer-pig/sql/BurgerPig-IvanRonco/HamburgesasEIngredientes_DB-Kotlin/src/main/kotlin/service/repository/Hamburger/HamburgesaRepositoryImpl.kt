package service.repository.Hamburger

import model.Hamburgesa
import mu.KotlinLogging
import service.database.ConfigDatabase
import java.util.*

class HamburgesaRepositoryImpl(
    private val configDatabase: ConfigDatabase
): HamburgesaRepository {

    private val logger = KotlinLogging.logger {}

    override fun findByName(name: String): List<Hamburgesa> {
        logger.debug { "Se buscan hamburgesas según su nombre" }

        return configDatabase.getAllHamburgesas().filter { it.nombre.lowercase() == name.lowercase() }
    }

    override fun getAllIdIngredientesByHamburgesaId(id: UUID): List<Long> {
        logger.debug { "Consigo todos los ids de los ingredientes de la hamburgesa con id: $id" }

        return configDatabase.getAllIngredientesIdByHamburgesaId(id)
    }

    override fun findById(id: UUID): Hamburgesa? {
        logger.debug { "Se busca una hamburgesa según su id" }

        return configDatabase.getAllHamburgesas().firstOrNull{ it.id == id }
    }

    override fun getAll(): List<Hamburgesa> {
        logger.debug { "Se consiguen hamburgesas" }

        return configDatabase.getAllHamburgesas()
    }

    override fun save(entity: Hamburgesa): Hamburgesa {
        logger.debug { "Guargo/Actualizo una hamburgesa en la DB" }

        return findById(entity.id)?.let {
            update(entity)
        } ?: run {
            add(entity)
        }
    }

    private fun add(entity: Hamburgesa): Hamburgesa {
        logger.debug { "Se añade una hamburgesa" }

        return configDatabase.createHamburgesa(entity)
    }

    private fun update(entity: Hamburgesa): Hamburgesa {
        logger.debug { "Se actualiza una hamburgesa" }

        return configDatabase.updateHamburgesa(entity)
    }

    override fun delete(id: UUID): Boolean {
        logger.debug { "Se elimina una hamburgesa" }

        return configDatabase.deleteHamburgesasById(id)
    }

    override fun deleteAll() {
        logger.debug { "Elimino todas las hamburgesas" }

        configDatabase.deleteAllHamburgesas()
    }
}