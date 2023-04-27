package repositorio.vehiculos

import models.vehiculo.Vehiculo
import repositorio.base.CrudRepository
import java.util.UUID

interface VehiculoRepositoryBase: CrudRepository<Vehiculo, UUID> {
    fun findByAñoMatriculacion(añoMatriculacion: Int): List<Vehiculo>
    fun findByApto(apto: Boolean): List<Vehiculo>
    fun findByModelo(modelo: String): List<Vehiculo>
}