package controllers.modulo

import com.github.michaelbull.result.*
import errors.ModuloError
import models.Modulo
import mu.KotlinLogging
import repositories.modulo.ModuloRepository
import services.storage.modulo.ModuloStorageService
import validators.validate
import java.util.*

private val logger = KotlinLogging.logger {  }

class ModuloController(
    private val repo: ModuloRepository,
    private val storage: ModuloStorageService
): IModuloController {
    override fun findAll(): Iterable<Modulo> {
        logger.debug { "ModuloController ->\t" }
        return repo.findAll()
    }

    override fun findById(id: UUID): Result<Modulo, ModuloError> {
        logger.debug { "ModuloController ->\t" }
        return repo.findById(id)?.let { Ok(it) } ?: Err(ModuloError.ModuloNoEncontradoError())
    }

    override fun save(element: Modulo, storage: Boolean): Result<Modulo, ModuloError> {
        logger.debug { "ModuloController ->\t" }
        return element.validate().onSuccess {
            repo.save(element)
            if (storage) exportData()
        }
    }

    override fun saveAll(elements: Iterable<Modulo>, storage: Boolean): Result<Boolean, ModuloError> {
        logger.debug { "ModuloController ->\t" }
        elements.forEach {
            it.validate().onFailure {
                logger.error { "ModuloController ->\t${it.message}" }
                return Err(it)
            }
        }
        repo.saveAll(elements)
        if (storage) exportData()
        return Ok(true)
    }

    override fun deleteById(id: UUID): Result<Boolean, ModuloError> {
        logger.debug { "ModuloController ->\t" }
        return if (repo.deleteById(id)) Ok(true)
        else Err(ModuloError.ModuloNoEncontradoError())
    }

    override fun delete(element: Modulo): Result<Boolean, ModuloError> {
        logger.debug { "ModuloController ->\t" }
        return if (repo.delete(element)) Ok(true)
        else Err(ModuloError.ModuloNoEncontradoError())
    }

    override fun deleteAll() {
        logger.debug { "ModuloController ->\t" }
        repo.deleteAll()
    }

    override fun existsById(id: UUID): Result<Boolean, ModuloError> {
        logger.debug { "ModuloController ->\t" }
        return if (repo.existsById(id)) Ok(true)
        else Err(ModuloError.ModuloNoEncontradoError())
    }

    override fun exportData() {
        logger.debug { "ModuloController ->\t" }
        storage.saveAll(repo.findAll().toList())
    }

    override fun importData() {
        logger.debug { "ModuloController ->\t" }
        repo.deleteAll()
        repo.saveAll(storage.loadAll())
    }
}