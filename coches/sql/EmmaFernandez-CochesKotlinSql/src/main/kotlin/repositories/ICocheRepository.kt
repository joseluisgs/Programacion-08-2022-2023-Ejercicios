package repositories

import models.Coche

interface ICocheRepository : ICrudRepository<Coche, Long> {
    fun exportToJson()
}
