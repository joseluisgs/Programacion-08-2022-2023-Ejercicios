package repositories.hamburguesa

import models.Hamburguesa
import repositories.base.CrudRepository

interface HamburguesaRepository: CrudRepository<Hamburguesa, Long>