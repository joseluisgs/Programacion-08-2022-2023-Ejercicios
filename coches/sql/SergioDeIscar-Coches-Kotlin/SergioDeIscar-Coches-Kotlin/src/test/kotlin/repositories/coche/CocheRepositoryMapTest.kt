package repositories.coche

import factories.CocheFactory.getCochesDefault
import models.Coche
import repositories.CrudRepository
import repositories.RepositoryGenericTest

class CocheRepositoryMapTest: RepositoryGenericTest<Coche, Long>() {
    override fun getRepository() = CocheRepositoryMap()

    override fun getDefault() = getCochesDefault()

    override fun getUpdated() = getCochesDefault()[0].copy(marca = "Ferrari")

    override fun getNew() = getCochesDefault()[0].copy(id = 10L, marca = "Ferrari")

    override fun getOkId() = 1L

    override fun getFailId() = 100L
}