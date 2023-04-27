package service.repository.Hamburger

import model.Hamburgesa
import service.repository.base.CrudRepository
import java.util.UUID

interface HamburgesaRepository: CrudRepository<Hamburgesa, UUID> {
    fun findByName(name: String): List<Hamburgesa>
    fun getAllIdIngredientesByHamburgesaId(id: UUID): List<Long>
}