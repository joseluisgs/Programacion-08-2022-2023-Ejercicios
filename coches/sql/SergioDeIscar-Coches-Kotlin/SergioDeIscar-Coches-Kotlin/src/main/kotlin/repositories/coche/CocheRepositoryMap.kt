package repositories.coche

import models.Coche
import mu.KotlinLogging

private val logger = KotlinLogging.logger {  }

class CocheRepositoryMap: CocheRepository {
    private val coches = mutableMapOf<Long,Coche>()
    override fun findAll(): Iterable<Coche> {
        logger.debug { "CocheRepositoryMap ->\tfindAll" }
        return coches.values.toList()
    }

    override fun findById(id: Long): Coche? {
        logger.debug { "CocheRepositoryMap ->\tfindById" }
        return coches[id]
    }

    override fun save(element: Coche): Coche {
        logger.debug { "CocheRepositoryMap ->\tsave" }
        if (element.id <= 0){ // Simula el comportamiento de un autoincremental
            element.id = coches.keys.maxOrNull()?.plus(1) ?: 1
        }
        coches[element.id] = element
        return element
    }

    override fun saveAll(elements: Iterable<Coche>) {
        logger.debug { "CocheRepositoryMap ->\tsaveAll" }
        elements.forEach { save(it) }
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "CocheRepositoryMap ->\tdeleteById" }
        return coches.remove(id) != null
    }

    override fun delete(element: Coche): Boolean {
        logger.debug { "CocheRepositoryMap ->\tdelete" }
        return deleteById(element.id)
    }

    override fun deleteAll() {
        logger.debug { "CocheRepositoryMap ->\tdeleteAll" }
        coches.clear()
    }

    override fun existsById(id: Long): Boolean {
        logger.debug { "CocheRepositoryMap ->\texistsById" }
        return findById(id) != null
    }
}