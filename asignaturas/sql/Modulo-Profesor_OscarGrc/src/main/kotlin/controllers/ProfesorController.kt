package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ProfesorErrors
import models.Profesor
import mu.KotlinLogging
import repositories.ProfesorRepository
import storage.profesor.ProfesorStorage
import validators.validate

class ProfesorController(
    private val profesorRepositorio:ProfesorRepository = ProfesorRepository,
    private val profesorStorage: ProfesorStorage = ProfesorStorage){
    private val logger = KotlinLogging.logger {}

    fun findAll(): List<Profesor> {
        logger.info { "findAll CONTROLLER" }
        return profesorRepositorio.findAll().toList()
    }

    fun findById(id: Long): Result<Profesor, ProfesorErrors> {
        logger.info { "Obteniendo la persona con id: $id" }
        profesorRepositorio.findById(id).let {
            return if (it != null) {
                Ok(it)
            } else {
                Err(ProfesorErrors.ProfesorNoEncontrado("No se ha encontrado el profesor con id:$id"))
            }
        }
    }
    fun save(entity: Profesor): Result<Profesor, ProfesorErrors> {
        logger.info { "Save CONTROLLER " }
        entity.validate()
        profesorRepositorio.save(entity).let {
            if (it.id <= 0) {
                return  Err(ProfesorErrors.FalloAlSalvar("No se ha podido salvar el profesor:${entity.toString()}"))
            } else {
                return Ok(it)
            }
        }
    }
    fun update(entity: Profesor):Result<Profesor, ProfesorErrors>{
        logger.info { "update CONTROLLER " }
        entity.validate()
        profesorRepositorio.update(entity).let {
            if (it==null) {
                return  Err(ProfesorErrors.FalloAlActualizar("No se ha podido salvar el profesor:${entity.toString()}"))
            } else {
                return Ok(it)
            }
        }
    }

    fun deleteById(id: Long): Result<Boolean, ProfesorErrors> {
        logger.info { "deleteByID CONTROLER" }
        profesorRepositorio.deleteById(id).let {
            return if (it) {
                Ok(true)
            } else {
                Err(ProfesorErrors.ProfesorNoEncontrado("No se ha encontrado el profesor con id:$id"))
            }
        }
    }
    fun exportToCsv() {
        val salida = findAll()
    profesorStorage.exportToCsv(salida)
    }

    fun exportToJson() {
        val salida = findAll()
        profesorStorage.exportToJson(salida)
    }
     fun exportToXml() {
         val salida = findAll()
         profesorStorage.exportToXml(salida)
    }
     fun loadData(): List<Profesor> {
     return profesorStorage.loadData()
    }

    }