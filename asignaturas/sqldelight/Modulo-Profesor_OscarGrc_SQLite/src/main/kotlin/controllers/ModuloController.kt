package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.ModuloErrors
import models.Modulo
import mu.KotlinLogging
import repositories.ModuloRepository
import services.storage.modulo.ModuloStorage

class ModuloController @OptIn(ExperimentalStdlibApi::class) constructor(
    private val moduloRepositorio: ModuloRepository = ModuloRepository,
    private val moduloStorage: ModuloStorage = ModuloStorage
    ) {
        private val logger = KotlinLogging.logger {}
        fun findAll(): List<Modulo> {
            logger.info { "findAll CONTROLLER" }
            return moduloRepositorio.findAll().toList()
        }

        @OptIn(ExperimentalStdlibApi::class)
        fun findByUUID(uuid:String): Result<Modulo, ModuloErrors> {
            logger.info { "find by UUID : $uuid" }
            moduloRepositorio.findByUUID(uuid).let {
                return if (it != null) {
                    Ok(it)
                } else {
                    Err(ModuloErrors.ModuloNoEncontrado("No se ha encontrado el modulo con uuid:$uuid"))
                }
            }
        }

        @OptIn(ExperimentalStdlibApi::class)
        fun save(entity: Modulo): Modulo {
            logger.info { "Save CONTROLLER " }
            moduloRepositorio.save(entity).let {
                return it
            }
        }
        fun update(entity: Modulo): Result<Modulo, ModuloErrors> {
            logger.info { "update CONTROLLER " }
            moduloRepositorio.update(entity).let {
                if (it==null) {
                    return  Err(ModuloErrors.FalloAlActualizar("No se ha podido salvar el modulo:${entity.toString()}"))
                } else {
                    return Ok(it)
                }
            }
        }
        @OptIn(ExperimentalStdlibApi::class)
        fun deleteByUUID(uuid:String): Result<Boolean, ModuloErrors> {
            logger.info { "deleteByID CONTROLER" }
            moduloRepositorio.deleteByUUID(uuid).let {
                return if (it) {
                    Ok(true)
                } else {
                    Err(ModuloErrors.ModuloNoEncontrado("No se ha encontrado el modulo con id:$uuid"))
                }
            }
        }
        fun exportToCsv() {
            val salida = findAll()
            moduloStorage.exportToCsv(salida)
        }
        @OptIn(ExperimentalStdlibApi::class)
        fun exportToJson() {
            val salida = findAll()
            moduloStorage.exportToJson(salida)
        }
        fun exportToXml() {
            val salida = findAll()
            moduloStorage.exportToXml(salida)
        }
        fun loadData(): List<Modulo> {
            return moduloStorage.loadData()
        }

    }