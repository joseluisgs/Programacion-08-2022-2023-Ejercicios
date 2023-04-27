package repository.profesores

import models.Profesor
import repository.base.CrudRepository

interface ProfesorRepositoryBase: CrudRepository<Profesor, Long> {
    fun findByModulo(modulo: String): List<Profesor>
}