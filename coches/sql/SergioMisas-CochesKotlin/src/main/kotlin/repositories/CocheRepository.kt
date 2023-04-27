package repositories

import models.Coche

interface CocheRepository: CrudRepository<Coche, Long> {
    fun leerCsv():List<Coche>
    fun escribirJson()
}
