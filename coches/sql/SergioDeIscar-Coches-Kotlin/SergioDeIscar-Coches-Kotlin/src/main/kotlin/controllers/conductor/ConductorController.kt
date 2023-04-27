package controllers.conductor

import com.github.michaelbull.result.*
import errors.ConductorError
import models.Conductor
import mu.KotlinLogging
import repositories.conductor.ConductorRepository
import services.storage.conductor.ConductorStorageService
import validators.validate
import java.util.*

private val logger = KotlinLogging.logger {  }

class ConductorController(
    private val repo: ConductorRepository,
    private val storage: ConductorStorageService
) : IConductorController{
    override fun findAll(): Iterable<Conductor> {
        logger.debug { "ConductorController ->\tfindAll" }
        return repo.findAll()
    }

    override fun findById(id: UUID): Result<Conductor, ConductorError> {
        logger.debug { "ConductorController ->\tfindById" }
        return repo.findById(id)?.let { Ok(it) } ?: Err(ConductorError.ConductorNoEncontradoError())
    }

    override fun save(element: Conductor, storage: Boolean): Result<Conductor, ConductorError> {
        logger.debug { "ConductorController ->\tsave" }
        return element.validate().onSuccess {
            repo.save(element)
            if (storage){
                exportData()
            }
        }
    }

    override fun saveAll(elements: Iterable<Conductor>, storage: Boolean): Result<Boolean, ConductorError> {
        logger.debug { "ConductorController ->\tsaveAll" }
        elements.forEach { it.validate().onFailure {
            return Err(it)
        } }
        repo.saveAll(elements)
        return Ok(true)
    }

    override fun deleteById(id: UUID): Result<Boolean, ConductorError> {
        logger.debug { "ConductorController ->\tdeleteById" }
        return if (repo.deleteById(id)){
            Ok(true)
        }else{
            Err(ConductorError.ConductorNoEncontradoError())
        }
    }

    override fun delete(element: Conductor): Result<Boolean, ConductorError> {
        logger.debug { "ConductorController ->\tdelete" }
        return if (repo.delete(element)){
            Ok(true)
        }else{
            Err(ConductorError.ConductorNoEncontradoError())
        }
    }

    override fun deleteAll() {
        logger.debug { "ConductorController ->\tdeleteAll" }
        repo.deleteAll()
    }

    override fun existsById(id: UUID): Result<Boolean, ConductorError> {
        logger.debug { "ConductorController ->\texistsById" }
        return if (repo.existsById(id)){
            Ok(true)
        }else{
            Err(ConductorError.ConductorNoEncontradoError())
        }
    }

    override fun exportData() {
        logger.debug { "ConductorController ->\texportData" }
        storage.saveAll(repo.findAll().toList())
    }

    override fun importData() {
        logger.debug { "ConductorController ->\timportData" }
        repo.deleteAll()
        repo.saveAll(storage.loadAll())
    }
}