package controller

import exceptions.ProfesorException
import models.Profesor
import mu.KotlinLogging
import repositorio.profesores.ProfesoresRepositoryBase
import service.storage.profesores.ProfesorStorageService
import validator.validate
import java.util.*

class ProfesoresController(
    val repository: ProfesoresRepositoryBase,
    val storage: ProfesorStorageService
) {

    private val logger = KotlinLogging.logger {  }

    fun findByExperiencia(experiencia: Int): List<Profesor> {
        logger.info { "Buscamos segun los años de experiencia: $experiencia" }
        return repository.findByExperiencia(experiencia)
    }

    fun findByUuid(uuid: UUID): Profesor {
        logger.info { "Buscamos segun uuid: $uuid" }
        return repository.findByUuid(uuid) ?: throw ProfesorException.ProfesorNotFoundException(uuid.toString())
    }

    fun findByName(name: String): List<Profesor> {
        logger.info { "Buscamos segun name: $name" }
        return repository.findByName(name)
    }

    fun findAll(): List<Profesor> {
        logger.info { "Buscamos todos los profesores" }
        return repository.findAll()
    }

    fun findById(id: Long): Profesor {
        logger.info { "Buscamos segun id: $id" }
        return repository.findById(id) ?: throw ProfesorException.ProfesorNotFoundException(id.toString())
    }

    fun save(entity: Profesor): Profesor {
        logger.info { "Guardamos/actualizamos un profesor" }
        entity.validate()
        return repository.save(entity)
    }

    fun delete(entity: Profesor): Boolean {
        logger.info { "Eliminamos un profesor" }
        return if(repository.delete(entity)){
            true
        }else{
            throw ProfesorException.ProfesorNotFoundException(entity.id.toString())
        }
    }

    fun deleteById(id: Long): Boolean {
        logger.info { "Eliminamos un profesor según su id: $id" }
        return if(repository.deleteById(id)){
            true
        }else{
            throw ProfesorException.ProfesorNotFoundException(id.toString())
        }
    }

    fun deleteAll(){
        logger.info { "Eliminamos todos los profesores" }
        repository.deleteAll()
    }

    fun exportData(){
        logger.info { "Exportamos los datos a la base de datos desde el fichero" }
        val profesores = repository.findAll()
        storage.safeAll(profesores)
    }

    fun importData(borramosTodo: Boolean = false){
        logger.info { "Importamos los datos a fichero desde la base de datos" }

        if(borramosTodo) deleteAll()

        val profesores = storage.loadAll()

        profesores.forEach {
            save(it)
        }
    }
}