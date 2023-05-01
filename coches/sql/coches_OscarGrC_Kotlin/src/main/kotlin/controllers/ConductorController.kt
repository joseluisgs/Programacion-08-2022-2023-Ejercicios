package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import errors.CochesErrors
import errors.ConductorErrors
import models.Coche
import models.Conductor
import mu.KotlinLogging
import repositories.CocheRepository
import repositories.ConductorRepository
import storage.coches.CochesStorage
import storage.conductores.ConductorStorage

class ConductorController @OptIn(ExperimentalStdlibApi::class) constructor(
    private val conductoresRepositorio: ConductorRepository = ConductorRepository,
    private val conductorStorage: ConductorStorage = ConductorStorage
    ) {
        private val logger = KotlinLogging.logger {}
        fun findAll(): List<Conductor> {
            logger.info { "findAll CONTROLLER" }
            return conductoresRepositorio.findAll().toList()
        }

        @OptIn(ExperimentalStdlibApi::class)
        fun findByUUID(uuid:String): Result<Conductor, ConductorErrors> {
            logger.info { "find by UUID : $uuid" }
            conductoresRepositorio.findByUUID(uuid).let {
                return if (it != null) {
                    Ok(it)
                } else {
                    Err(ConductorErrors.ConductorNoEncontrado("No se ha encontrado el conductor con id:$uuid"))
                }
            }
        }

        @OptIn(ExperimentalStdlibApi::class)
        fun save(entity: Conductor): Conductor {
            logger.info { "Save CONTROLLER " }
            conductoresRepositorio.save(entity).let {
                return it
            }
        }
        fun update(entity: Conductor): Result<Conductor, ConductorErrors> {
            logger.info { "update CONTROLLER " }
            conductoresRepositorio.update(entity).let {
                if (it==null) {
                    return  Err(ConductorErrors.FalloAlActualizar("No se ha podido salvar el coche:${entity.toString()}"))
                } else {
                    return Ok(it)
                }
            }
        }
        @OptIn(ExperimentalStdlibApi::class)
        fun deleteByUUID(uuid:String): Result<Boolean, ConductorErrors> {
            logger.info { "deleteByID CONTROLER" }
            conductoresRepositorio.deleteByUUID(uuid).let {
                return if (it) {
                    Ok(true)
                } else {
                    Err(ConductorErrors.ConductorNoEncontrado("No se ha encontradocoche con id:$uuid"))
                }
            }
        }
        fun exportToCsv() {
            val salida = findAll()
            conductorStorage.exportToCsv(salida)
        }
        @OptIn(ExperimentalStdlibApi::class)
        fun exportToJson() {
            val salida = findAll()
            conductorStorage.exportToJson(salida)
        }
        fun exportToXml() {
            val salida = findAll()
            conductorStorage.exportToXml(salida)
        }
        fun loadData(): List<Conductor> {
            return conductorStorage.loadData()
        }

    }