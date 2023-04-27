package controller

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import error.ConductorError
import models.conductor.Conductor
import mu.KotlinLogging
import repositorio.conductor.ConductorRepositoryBase
import validator.conductores.validate
import java.util.*

data class ConductorController(
    private val repository: ConductorRepositoryBase
) {

    private val logger = KotlinLogging.logger {  }

    fun findByDni(dni: String): Result<Conductor, ConductorError> {
        logger.info { "Buscamos el conductor con el dni: $dni" }

        return repository.findByDni(dni)?.let {
            Ok(it)
        } ?: run {
            Err(ConductorError.ConductorNotFound(dni))
        }
    }

    fun findByEdad(edad: Int): List<Conductor> {
        logger.info { "Buscamos los conductores con la edad: $edad" }

        return repository.findByEdad(edad)
    }

    fun findByNombre(nombre: String): List<Conductor> {
        logger.info { "Buscamos los conductores con el nombre: $nombre" }

        return repository.findByNombre(nombre)
    }

    fun findAll(): List<Conductor> {
        logger.info { "Conseguimos todos los conductores" }

        return repository.findAll()
    }

    fun findById(uuid: UUID): Result<Conductor, ConductorError> {
        logger.info { "Buscamos el conductor con el uuid: $uuid" }

        return repository.findById(uuid) ?.let {
            Ok(it)
        } ?: run {
            Err(ConductorError.ConductorNotFound(uuid.toString()))
        }
    }

    fun save(entity: Conductor): Result<Conductor, ConductorError> {
        logger.info { "Guardamos/Actualizamos un conductor" }

        return entity.validate().andThen {
            Ok(repository.save(it))
        }
    }

    fun delete(entity: Conductor): Result<Boolean, ConductorError> {
        logger.info { "Borramos un conductor" }

        return if(repository.delete(entity)){
            Ok(true)
        } else {
            Err(ConductorError.ConductorNotFound(entity.uuid.toString()))
        }
    }

    fun deleteById(uuid: UUID): Result<Boolean, ConductorError> {
        logger.info { "Borramos el conductor con el uuid: $uuid" }

        return if(repository.deleteById(uuid)){
            Ok(true)
        } else {
            Err(ConductorError.ConductorNotFound(uuid.toString()))
        }
    }

    fun deleteAll(dropAll: Boolean) {
        logger.info { "Borramos todos los conductores" }

        repository.deleteAll(dropAll)
    }
}