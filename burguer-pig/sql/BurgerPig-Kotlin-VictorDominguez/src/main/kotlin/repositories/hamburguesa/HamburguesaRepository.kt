package repositories.hamburguesa

import models.Hamburguesa
import repositories.CrudRepository
import java.util.UUID

interface HamburguesaRepository: CrudRepository<Hamburguesa, UUID> {
}