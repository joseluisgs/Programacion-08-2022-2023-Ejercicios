package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.CochesErrors
import models.Coche
import mu.KotlinLogging
import repositories.CocheRepository
import services.storage.coches.CochesStorage
import validators.validate

class CocheController(
    private val cochesRepositorio:CocheRepository = CocheRepository,
    private val cochesStorage: CochesStorage = CochesStorage){
    private val logger = KotlinLogging.logger {}

    fun findAll(): List<Coche> {
        logger.info { "findAll CONTROLLER" }
        return cochesRepositorio.findAll().toList()
    }

    fun findById(id: Long): Result<Coche, CochesErrors> {
        logger.info { "Obteniendo la persona con id: $id" }
        cochesRepositorio.findById(id).let {
            return if (it != null) {
                Ok(it)
            } else {
                Err(CochesErrors.CocheNoEncontrado("No se ha encontrado el coche con id:$id"))
            }
        }
    }
    fun save(entity: Coche): Result<Coche, CochesErrors> {
        logger.info { "Save CONTROLLER " }
        entity.validate()
        cochesRepositorio.save(entity).let {
            if (it.id <= 0) {
                return  Err(CochesErrors.FalloAlSalvar("No se ha podido salvar el coche:${entity.toString()}"))
            } else {
                return Ok(it)
            }
        }
    }
    fun update(entity: Coche):Result<Coche, CochesErrors>{
        logger.info { "update CONTROLLER " }
        entity.validate()
        cochesRepositorio.update(entity).let {
            if (it==null) {
                return  Err(CochesErrors.FalloAlActualizar("No se ha podido salvar el coche:${entity.toString()}"))
            } else {
                return Ok(it)
            }
        }
    }

    fun deleteById(id: Long): Result<Boolean, CochesErrors> {
        logger.info { "deleteByID CONTROLER" }
        cochesRepositorio.deleteById(id).let {
            return if (it) {
                Ok(true)
            } else {
                Err(CochesErrors.CocheNoEncontrado("No se ha encontradocoche con id:$id"))
            }
        }
    }
    fun exportToCsv() {
        val salida = findAll()
    cochesStorage.exportToCsv(salida)
    }

    fun exportToJson() {
        val salida = findAll()
        cochesStorage.exportToJson(salida)
    }
     fun exportToXml() {
         val salida = findAll()
         cochesStorage.exportToXml(salida)
    }
     fun loadData(): List<Coche> {
     return cochesStorage.loadData()
    }

    }