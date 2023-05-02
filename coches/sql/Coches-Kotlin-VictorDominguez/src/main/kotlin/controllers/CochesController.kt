package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import exceptions.CocheException
import exceptions.CocheNoEncontradoException
import exceptions.CocheNoValidoException
import models.Coche
import mu.KotlinLogging
import repositories.coche.CochesRepository
import services.storage.coche.CochesService
import validators.validar
import kotlin.math.log

private val logger = KotlinLogging.logger{}

class CochesController(
    private val repository: CochesRepository,
    private val csvStorage: CochesService,
    private val jsonStorage: CochesService
) {
    init {
        importarDeCsv()
    }

    fun exportarAJson() {
        logger.debug { "CochesController -> Exportando de la base de datos a JSON" }
        jsonStorage.exportar(repository.exportar())
    }

    fun importarDeCsv() {
        logger.debug { "CochesController -> Importando de CSV a la base de datos" }
        repository.importar(csvStorage.importar())
    }

    fun buscarTodos(): List<Coche> {
        logger.debug { "CochesController -> Buscar todos" }
        return repository.buscarTodos()
    }

    fun buscarPorId(id: Long): Coche? {
        logger.debug { "CochesController -> Buscar por ID: $id" }
        return repository.buscarPorId(id)
    }

    fun eliminarTodos() {
        logger.debug { "CochesController -> Eliminar todos" }
        return repository.eliminarTodos()
    }

    fun eliminarPorId(id: Long): Result<Coche, CocheException> {
        logger.debug { "CochesController -> Eliminar por ID: $id" }
        return repository.eliminarPorId(id)?.let { Ok(it) }
            ?: Err(CocheNoEncontradoException("No se ha encontrado el coche con el ID: $id"))
    }

    fun salvar(item: Coche) {
        logger.debug { "CochesController -> Salvar coche: $item" }
        item.validar()
        repository.salvar(item)
    }
}