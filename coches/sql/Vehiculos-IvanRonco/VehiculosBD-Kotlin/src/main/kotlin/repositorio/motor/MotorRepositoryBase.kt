package repositorio.motor

import models.motor.Motor
import repositorio.base.CrudRepository
import java.util.UUID

interface MotorRepositoryBase:  CrudRepository<Motor, UUID>{
    fun findMotorByModel(modelo: String): List<Motor>
}