package repositories.modulo

import models.Modulo
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {  }

class ModuloRepositoryMap: ModuloRepository {
    private val map = mutableMapOf<UUID, Modulo>()
    override fun findAll(): Iterable<Modulo> {
        logger.debug { "ModuloRepositoryMap ->\tfindAll" }
        return map.values
    }

    override fun findById(id: UUID): Modulo? {
        logger.debug { "ModuloRepositoryMap ->\tfindById" }
        return map[id]
    }

    override fun save(element: Modulo): Modulo {
        logger.debug { "ModuloRepositoryMap ->\tsave" }
        map[element.uuid] = element
        return element
    }

    override fun saveAll(elements: Iterable<Modulo>) {
        logger.debug { "ModuloRepositoryMap ->\tsaveAll" }
        elements.forEach { map[it.uuid] = it }
    }

    override fun deleteById(id: UUID): Boolean {
        logger.debug { "ModuloRepositoryMap ->\tdeleteById" }
        return map.remove(id) != null
    }

    override fun delete(element: Modulo): Boolean {
        logger.debug { "ModuloRepositoryMap ->\tdelete" }
        return map.remove(element.uuid) != null
    }

    override fun deleteAll() {
        logger.debug { "ModuloRepositoryMap ->\tdeleteAll" }
        map.clear()
    }

    override fun existsById(id: UUID): Boolean {
        logger.debug { "ModuloRepositoryMap ->\texistsById" }
        return map.containsKey(id)
    }
}