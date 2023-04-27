package controllers.coche

import com.github.michaelbull.result.*
import errors.CocheError
import models.Coche
import mu.KotlinLogging
import repositories.coche.CocheRepository
import services.storage.coche.CocheStorageService
import validators.validate

private val logger = KotlinLogging.logger {  }

class CocheController(
    private val repo: CocheRepository,
    private val storage: CocheStorageService
): ICocheController {
    override fun findAll(): Iterable<Coche> {
        logger.debug { "CocheController ->\tfindAll" }
        return repo.findAll()
    }

    override fun findById(id: Long): Result<Coche, CocheError> {
        logger.debug { "CocheController ->\tfindById" }
        return repo.findById(id)?.let { Ok(it) } ?: Err(CocheError.CocheNoEncontradoError())
    }

    override fun save(element: Coche, storage: Boolean): Result<Coche, CocheError> {
        logger.debug { "CocheController ->\tsave" }
        return element.validate().onSuccess {
            repo.save(it)
            if (storage) exportData()
        }
    }

    override fun saveAll(elements: Iterable<Coche>, storage: Boolean): Result<Boolean, CocheError> {
        logger.debug { "CocheController ->\tsaveAll" }
        elements.forEach { it.validate().onFailure {
            return Err(it)
        }}
        repo.saveAll(elements)
        return Ok(true)
    }

    override fun deleteById(id: Long): Result<Boolean, CocheError> {
        logger.debug { "CocheController ->\tdeleteById" }
        return if (repo.deleteById(id)){
            Ok(true)
        } else Err(CocheError.CocheNoEncontradoError())
    }

    override fun delete(element: Coche): Result<Boolean, CocheError> {
        logger.debug { "CocheController ->\tdelete" }
        return if (repo.delete(element)){
            Ok(true)
        } else Err(CocheError.CocheNoEncontradoError())
    }

    override fun deleteAll() {
        logger.debug { "CocheController ->\tdeleteAll" }
        repo.deleteAll()
    }

    override fun existsById(id: Long): Result<Boolean, CocheError> {
        logger.debug { "CocheController ->\texistsById" }
        return if (repo.existsById(id)){
            Ok(true)
        } else Err(CocheError.CocheNoEncontradoError())
    }

    override fun exportData() {
        logger.debug { "CocheController ->\texportData" }
        storage.saveAll(repo.findAll().toList())
    }

    override fun importData() {
        logger.debug { "CocheController ->\timportData" }
        repo.deleteAll()
        repo.saveAll(storage.loadAll())
    }
}