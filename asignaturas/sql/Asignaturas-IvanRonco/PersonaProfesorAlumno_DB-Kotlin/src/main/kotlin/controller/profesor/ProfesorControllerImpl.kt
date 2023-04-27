package controller.profesor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import error.PersonaError
import models.Profesor
import mu.KotlinLogging
import repository.profesores.ProfesorRepositoryBase
import service.database.profesores.ProfesorStorageService
import validator.validate

class ProfesorControllerImpl(
    private val repository: ProfesorRepositoryBase,
    private val storage: ProfesorStorageService
): ProfesorControllerBase {

    private val logger = KotlinLogging.logger {  }

    override fun findByModulo(modulo: String): List<Profesor> {
        logger.info { "Conseguimos a todos los profesores, según el módulo: $modulo" }

        return repository.findByModulo(modulo)
    }

    override fun findAll(): List<Profesor> {
        logger.info { "Conseguimos a todos los profesores" }

        return repository.findAll()
    }

    override fun findById(id: Long): Result<Profesor, PersonaError> {
        logger.info { "Conseguimos al profesor con id: $id" }

        return repository.findById(id)?.let {
            Ok(it)
        } ?: run {
            Err(PersonaError.PersonaNotFound(id.toString()))
        }
    }

    override fun save(entity: Profesor): Result<Profesor, PersonaError> {
        logger.info { "Guardo/Actualizo un profesor" }

        return entity.validate().andThen {
            Ok(repository.save(entity))
        }
    }

    override fun saveAll(entities: List<Profesor>) {
        logger.info { "Guardamos/Actualizamos los profesores" }

        entities.forEach {
            repository.save(it)
        }
    }

    override fun delete(entity: Profesor): Result<Boolean, PersonaError> {
        logger.info { "Borramos un profesor" }

        return if(repository.delete(entity) == true){
            Ok(true)
        } else {
            Err(PersonaError.PersonaNotFound(entity.id.toString()))
        }
    }

    override fun deleteById(id: Long): Result<Boolean, PersonaError> {
        logger.info { "Borramos el profesor con el id: $id" }

        return if(repository.deleteById(id) == true){
            Ok(true)
        } else {
            Err(PersonaError.PersonaNotFound(id.toString()))
        }
    }

    override fun deleteAll() {
        logger.info { "Borramos todos los profesor" }

        repository.deleteAll()
    }

    fun import(deleteData: Boolean = false){
        logger.debug { "Importamos los datos del fichero a la BD" }

        if(deleteData){
            deleteAll()
        }

        storage.loadAll().filterIsInstance<Profesor>().forEach {
            repository.save(it)
        }
    }

    fun export(){
        logger.debug { "Exportamos los datos de la BD al fichero" }

        storage.saveAll(repository.findAll())
    }
}