package repositorio.vehiculos

import error.VehiculoError
import models.vehiculo.Vehiculo
import repositorio.base.BaseRepository
import java.util.UUID

interface VehiculoRepositoryBase: BaseRepository<Vehiculo, UUID> {
    fun findByAñoMatriculacion(añoMatriculacion: Int): List<Vehiculo>
    fun findByApto(apto: Boolean): List<Vehiculo>
    fun findByModelo(modelo: String): List<Vehiculo>
}