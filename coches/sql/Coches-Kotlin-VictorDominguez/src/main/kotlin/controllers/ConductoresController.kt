package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import exceptions.*
import models.Coche
import models.Conductor
import mu.KotlinLogging
import repositories.conductor.ConductoresRepository
import services.storage.conductor.ConductoresService
import validators.validar
import java.util.*

private val logger = KotlinLogging.logger{}

class ConductoresController(
    private val repository: ConductoresRepository,
    private val csvStorage: ConductoresService,
    private val jsonStorage: ConductoresService
) {
    init {
        importarDeCsv()
    }

    fun exportarAJson() {
        logger.debug { "ConductoresController -> Exportando de la base de datos a JSON" }
        jsonStorage.exportar(repository.exportar())
    }

    fun importarDeCsv() {
        logger.debug { "ConductoresController -> Importando de CSV a la base de datos" }
        repository.importar(csvStorage.importar())
    }

    fun buscarTodos(): List<Conductor> {
        logger.debug { "ConductoresController -> Buscar todos" }
        return repository.buscarTodos()
    }

    fun buscarPorId(id: UUID): Conductor? {
        logger.debug { "ConductoresController -> Buscar por ID: $id" }
        return repository.buscarPorId(id)
    }

    fun eliminarTodos() {
        logger.debug { "ConductoresController -> Eliminar todos" }
        return repository.eliminarTodos()
    }

    fun eliminarPorId(id: UUID): Result<Conductor, ConductorException> {
        logger.debug { "ConductoresController -> Eliminar por id: $id" }
        return repository.eliminarPorId(id)?.let { Ok(it) }
            ?: Err(ConductorNoEncontradoException("No se ha encontrado el conductor con el ID: $id"))
    }

    fun salvar(item: Conductor) {
        logger.debug { "ConductorController -> Salvar conductor: $item" }
        item.validar()
        repository.salvar(item)
    }

}