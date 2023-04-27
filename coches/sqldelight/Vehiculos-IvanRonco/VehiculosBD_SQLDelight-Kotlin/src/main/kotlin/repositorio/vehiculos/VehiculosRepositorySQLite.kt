package repositorio.vehiculos

import mapper.toMotor
import mapper.toMotorEntity
import mapper.toVehiculo
import models.motor.*
import models.vehiculo.Vehiculo
import mu.KotlinLogging
import service.database.vehiculos.ConfigDatabase
import java.util.*

object VehiculosRepositorySQLite: VehiculoRepositoryBase {

    val db = ConfigDatabase

    private val logger = KotlinLogging.logger {  }

    override fun findByAñoMatriculacion(añoMatriculacion: Int): List<Vehiculo> {
        logger.debug { "Buscamos segun el año de matriculacion: $añoMatriculacion" }

        return findAll().filter { it.añoMatriculacion == añoMatriculacion }
    }

    override fun findByApto(apto: Boolean): List<Vehiculo> {
        logger.debug { "Buscamos según apto: $apto" }

        return findAll().filter { it.apto == apto }
    }

    override fun findByModelo(modelo: String): List<Vehiculo> {
        logger.debug { "Buscamos segun modelo: $modelo" }

        return findAll().filter { it.modelo.lowercase().contains(modelo.lowercase()) }
    }

    override fun findAll(): List<Vehiculo> {
        logger.debug { "Buscamos todos los vehiculos" }

        return db.vehiculoQuery.getAllVehiculos().executeAsList().map { it.toVehiculo() }
    }

    private fun getMotor(motorId: String): Motor {
        logger.debug { "Buscamos un motor según su UUID" }

        return db.vehiculoQuery.getMotorByUUID(motorId).executeAsOne().toMotor()
    }

    override fun findById(uuid: UUID): Vehiculo? {
        logger.debug { "Buscamos segun uuid: $uuid" }
        return findAll().firstOrNull { it.uuid == uuid }
    }

    override fun save(entity: Vehiculo): Vehiculo {
        logger.debug { "Guardamos/actualizamos un vehiculo" }
        return findAll().firstOrNull{it.uuid == entity.uuid}?.let {
            update(entity)
        }?: run {
            create(entity)
        }
    }

    private fun create(entity: Vehiculo): Vehiculo {
        logger.debug { "Se inserta un nuevo vehiculo en la base de datos" }

        if(db.vehiculoQuery.getMotorByUUID(entity.motor.uuid.toString()).executeAsOneOrNull() == null){
            val motorEntity = entity.motor.toMotorEntity()
            db.vehiculoQuery.createMotor(
                uuid = motorEntity.uuid,
                modelo = motorEntity.modelo,
                caballos = motorEntity.caballos,
                tipo = motorEntity.tipo,
                cilindrada = motorEntity.cilindrada,
                capacidadElectrica = motorEntity.capacidadElectrica,
                capacidadGasolina = motorEntity.capacidadGasolina,
                carga = motorEntity.carga

            )
        }

        db.vehiculoQuery.createVehiculo(
            entity.uuid.toString(),
            entity.modelo,
            entity.kilometros.toLong(),
            entity.añoMatriculacion.toLong(),
            if(entity.apto) 1L else 0L,
            entity.motor.uuid.toString()
        )
        return entity
    }

    private fun update(entity: Vehiculo): Vehiculo {
        logger.debug { "Se actualiza un vehiculo de la base de datos" }

        db.vehiculoQuery.updateVehiculo(
            entity.modelo,
            entity.kilometros.toLong(),
            entity.añoMatriculacion.toLong(),
            if(entity.apto) 1L else 0L,
            entity.motor.uuid.toString(),
            entity.uuid.toString()
        )
        return entity
    }

    override fun delete(entity: Vehiculo): Boolean {
        logger.debug { "Eliminamos un vehiculo" }
        return deleteById(entity.uuid)
    }

    override fun deleteById(uuid: UUID): Boolean {
        logger.debug { "Eliminamos un vehiculo según su uuid: $uuid" }

        db.vehiculoQuery.deleteVehiculoByUUID(uuid.toString())
        return true
    }

    override fun deleteAll(dropMotores: Boolean) {
        logger.debug { "Eliminamos todos los vehiculos" }

        if(dropMotores){
            db.vehiculoQuery.deleteAllMotores()
        }

        db.vehiculoQuery.deleteAllVehiculos()
    }
}