package repositorio.profesores

import models.Profesor
import repositorio.base.BaseRepository
import java.util.UUID

interface ProfesoresRepositoryBase: BaseRepository<Profesor, Long> {
    fun findByExperiencia(experiencia: Int): List<Profesor>
    fun findByUuid(uuid: UUID): Profesor?
    fun findByName(name: String): List<Profesor>
}