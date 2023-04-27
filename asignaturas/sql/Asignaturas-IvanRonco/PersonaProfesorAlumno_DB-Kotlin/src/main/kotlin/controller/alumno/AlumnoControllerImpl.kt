package controller.alumno

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import error.PersonaError
import models.Alumno
import mu.KotlinLogging
import repository.alumnos.AlumnoRepositoryBase
import service.storage.alumnos.AlumnoStorageService
import validator.validate

class AlumnoControllerImpl(
    private val repository: AlumnoRepositoryBase,
    private val storage: AlumnoStorageService
): AlumnoControllerBase {

    private val logger = KotlinLogging.logger {  }

    override fun findByEdad(edad: Int): List<Alumno> {
        logger.info { "Conseguimos a todos los alumnos, según la edad: $edad" }

        return repository.findByEdad(edad)
    }

    override fun findAll(): List<Alumno> {
        logger.info { "Conseguimos a todos los alumnos" }

        return repository.findAll()
    }

    override fun findById(id: Long): Result<Alumno, PersonaError> {
        logger.info { "Conseguimos al alumno con id: $id" }

        return repository.findById(id)?.let {
            Ok(it)
        } ?: run {
            Err(PersonaError.PersonaNotFound(id.toString()))
        }
    }

    override fun save(entity: Alumno): Result<Alumno, PersonaError> {
        logger.info { "Guardo/Actualizo un alumno" }

        return entity.validate().andThen {
            Ok(repository.save(entity))
        }
    }

    override fun saveAll(entities: List<Alumno>) {
        logger.info { "Guardamos/Actualizamos los alumnos" }

        entities.forEach {
            repository.save(it)
        }
    }

    override fun delete(entity: Alumno): Result<Boolean, PersonaError> {
        logger.info { "Borramos un alumno" }

        return if(repository.delete(entity) == true){
            Ok(true)
        } else {
            Err(PersonaError.PersonaNotFound(entity.id.toString()))
        }
    }

    override fun deleteById(id: Long): Result<Boolean, PersonaError> {
        logger.info { "Borramos el alumno con el id: $id" }

        return if(repository.deleteById(id) == true){
            Ok(true)
        } else {
            Err(PersonaError.PersonaNotFound(id.toString()))
        }
    }

    override fun deleteAll() {
        logger.info { "Borramos todos los alumno" }

        repository.deleteAll()
    }

    fun import(deleteData: Boolean = false){
        logger.debug { "Importamos los datos del fichero a la BD" }

        if(deleteData){
            deleteAll()
        }

        storage.loadAll().filterIsInstance<Alumno>().forEach {
            repository.save(it)
        }
    }

    fun export(){
        logger.debug { "Exportamos los datos de la BD al fichero" }

        storage.saveAll(repository.findAll())
    }
}