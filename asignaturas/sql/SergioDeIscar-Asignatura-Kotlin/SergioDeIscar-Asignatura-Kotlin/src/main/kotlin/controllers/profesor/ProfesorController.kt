package controllers.profesor

import com.github.michaelbull.result.*
import errors.ProfesorError
import models.Profesor
import mu.KotlinLogging
import repositories.profesor.ProfesorRepository
import services.storage.profesor.ProfesorStorageService
import validators.validate

private val logger = KotlinLogging.logger {  }

class ProfesorController(
    private val repo: ProfesorRepository,
    private val storage: ProfesorStorageService
): IProfesorController {
    override fun findAll(): Iterable<Profesor> {
        logger.debug { "ProfesorController ->\tfindAll" }
        return repo.findAll()
    }

    override fun findById(id: Long): Result<Profesor, ProfesorError> {
        logger.debug { "ProfesorController ->\tfindById" }
        return repo.findById(id)?.let { Ok(it) } ?: Err(ProfesorError.ProfesorNoEncontradoError())
    }

    override fun save(element: Profesor, storage: Boolean): Result<Profesor, ProfesorError> {
        logger.debug { "ProfesorController ->\tsave" }
        return element.validate().onSuccess {
            repo.save(element)
            if (storage) exportData()
        }
    }

    override fun saveAll(elements: Iterable<Profesor>, storage: Boolean): Result<Boolean, ProfesorError> {
        logger.debug { "ProfesorController ->\tsaveAll" }
        elements.forEach{
            it.validate().onFailure {
                logger.error { "ProfesorController ->\t${it.message}" }
                return Err(it)
            }
        }
        repo.saveAll(elements)
        if (storage) exportData()
        return Ok(true)
    }

    override fun deleteById(id: Long): Result<Boolean, ProfesorError> {
        logger.debug { "ProfesorController ->\tdeleteById" }
        return if (repo.deleteById(id)) Ok(true)
        else Err(ProfesorError.ProfesorNoEncontradoError())
    }

    override fun delete(element: Profesor): Result<Boolean, ProfesorError> {
        logger.debug { "ProfesorController ->\tdelete" }
        return if (repo.delete(element)) Ok(true)
        else Err(ProfesorError.ProfesorNoEncontradoError())
    }

    override fun deleteAll() {
        logger.debug { "ProfesorController ->\tdeleteAll" }
        repo.deleteAll()
    }

    override fun existsById(id: Long): Result<Boolean, ProfesorError> {
        logger.debug { "ProfesorController ->\texistsById" }
        return if (repo.existsById(id)) Ok(true)
        else Err(ProfesorError.ProfesorNoEncontradoError())
    }

    override fun exportData() {
        logger.debug { "ProfesorController ->\texportData" }
        storage.saveAll(repo.findAll().toList())
    }

    override fun importData() {
        logger.debug { "ProfesorController ->\timportData" }
        repo.saveAll(storage.loadAll())
    }
}