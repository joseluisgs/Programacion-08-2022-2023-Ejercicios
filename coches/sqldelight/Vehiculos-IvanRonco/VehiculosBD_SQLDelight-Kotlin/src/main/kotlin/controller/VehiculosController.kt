package controller

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import error.VehiculoError
import models.vehiculo.Vehiculo
import mu.KotlinLogging
import repositorio.vehiculos.VehiculoRepositoryBase
import repositorio.vehiculos.VehiculosRepositorySQLite
import validator.validate
import java.util.*

class VehiculosController(
    val repository: VehiculoRepositoryBase
) {

    private val logger = KotlinLogging.logger {  }

    fun findByAñoMatriculacion(añoMatriculacion: Int): List<Vehiculo> {
        logger.info { "Buscamos segun el año de matriculacion: $añoMatriculacion" }
        return repository.findByAñoMatriculacion(añoMatriculacion)
    }

    fun findByApto(apto: Boolean): List<Vehiculo> {
        logger.info { "Buscamos segun apto: $apto" }
        return repository.findByApto(apto)
    }

    fun findByModelo(modelo: String): List<Vehiculo> {
        logger.info { "Buscamos segun modelo: $modelo" }
        return repository.findByModelo(modelo)
    }

    fun findAll(): List<Vehiculo> {
        logger.info { "Buscamos todos los vehiculos" }
        return repository.findAll()
    }

    fun findById(uuid: UUID): Result<Vehiculo, VehiculoError> {
        logger.info { "Buscamos segun uuid: $uuid" }
        return repository.findById(uuid)?.let {
            Ok(it)
        }?: run {
            Err(VehiculoError.VehiculoNotFound(uuid.toString()))
        }
    }

    fun save(entity: Vehiculo): Result<Vehiculo, VehiculoError> {
        logger.info { "Guardamos/actualizamos un vehiculo" }
        return entity.validate().andThen {
            Ok(repository.save(it))
        }
    }

    fun delete(entity: Vehiculo): Result<Boolean, VehiculoError> {
        logger.info { "Eliminamos un vehiculo" }
        return if(repository.delete(entity)) {
            Ok(true)
        }else {
            Err(VehiculoError.VehiculoNotFound("${entity.uuid}"))
        }
    }

    fun deleteById(uuid: UUID): Result<Boolean, VehiculoError> {
        logger.info { "Eliminamos un vehiculo según su uuid: $uuid" }
        return if(repository.deleteById(uuid)){
            Ok(true)
        }else {
            Err(VehiculoError.VehiculoNotFound("$uuid"))
        }
    }

    fun deleteAll(dropMotores: Boolean){
        logger.info { "Eliminamos todos los vehiculos" }
        repository.deleteAll(dropMotores)
    }
}