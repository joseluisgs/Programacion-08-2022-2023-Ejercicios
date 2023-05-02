package service.repository.Ingrediente

import model.Ingrediente
import mu.KotlinLogging
import org.koin.core.annotation.Single
import service.database.ConfigDatabase

@Single
class IngredienteRepositoryImpl(
    private val configDatabase: ConfigDatabase
): IngredienteRepository {

    private val logger = KotlinLogging.logger {  }

    override fun findById(id: Long): Ingrediente? {
        logger.debug { "Consigo el ingrediente de id: $id" }

        return configDatabase.getIngredienteById(id)
    }

    override fun getAll(): List<Ingrediente> {
        logger.debug { "Consigo todos los ingredientes de la DB" }

        return configDatabase.getAllIngredientes()
    }

    override fun save(entity: Ingrediente): Ingrediente {
        logger.debug { "Guardo/Actualizo un ingrediente" }

        return if(getAll().map { it.nombre.lowercase() }.contains(entity.nombre.lowercase())) {
            update(entity)
        } else {
            add(entity)
        }
    }

    private fun add(entity: Ingrediente): Ingrediente {
        logger.debug { "Añado un nuevo ingrediente en la DB" }

        return configDatabase.createIngrediente(entity)
    }

    private fun update(entity: Ingrediente): Ingrediente {
        logger.debug { "Actualizo un ingrediente de la DB" }

        return configDatabase.updateIngrediente(entity)
    }

    override fun delete(id: Long): Boolean {
        logger.debug { "Elimino el ingrediente de id: $id" }

        return configDatabase.deleteIngredienteById(id)
    }

    override fun deleteAll() {
        logger.debug { "Elimino todos los ingredientes" }

        configDatabase.deleteAllIngredientes()
    }
}