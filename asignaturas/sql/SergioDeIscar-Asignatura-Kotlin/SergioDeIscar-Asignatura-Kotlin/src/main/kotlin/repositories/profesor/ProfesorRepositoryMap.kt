package repositories.profesor

import models.Profesor
import mu.KotlinLogging

private val logger = KotlinLogging.logger {  }

class ProfesorRepositoryMap: ProfesorRepository {
    private val map = mutableMapOf<Long, Profesor>()
    override fun findAll(): Iterable<Profesor> {
        logger.debug { "ProfesorRepositoryMap ->\tfindAll" }
        return map.values
    }

    override fun findById(id: Long): Profesor? {
        logger.debug { "ProfesorRepositoryMap ->\tfindById" }
        return map[id]
    }

    override fun save(element: Profesor): Profesor {
        logger.debug { "ProfesorRepositoryMap ->\tsave" }
        map[element.id] = element
        return element
    }

    override fun saveAll(elements: Iterable<Profesor>) {
        logger.debug { "ProfesorRepositoryMap ->\tsaveAll" }
        elements.forEach { map[it.id] = it }
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "ProfesorRepositoryMap ->\tdeleteById" }
        return map.remove(id) != null
    }

    override fun delete(element: Profesor): Boolean {
        logger.debug { "ProfesorRepositoryMap ->\tdelete" }
        return map.remove(element.id) != null
    }

    override fun deleteAll() {
        logger.debug { "ProfesorRepositoryMap ->\tdeleteAll" }
        map.clear()
    }

    override fun existsById(id: Long): Boolean {
        logger.debug { "ProfesorRepositoryMap ->\texistsById" }
        return map.containsKey(id)
    }
}