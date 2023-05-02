package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.LineaHamburguesaException
import exceptions.LineaHamburguesaNoEncontradaException
import models.LineaHamburguesa
import mu.KotlinLogging
import repositories.linea_hamburguesa.LineaHamburguesaRepository
import services.storage.linea_hamburguesa.LineaHamburguesaService
import validators.validar

private val logger = KotlinLogging.logger{}

class LineaHamburguesaController(
    private val repository: LineaHamburguesaRepository,
    private val csvStorage: LineaHamburguesaService,
) {

    init {
        importarDeCsv()
    }

    fun importarDeCsv() {
        logger.debug { "LineaHamburguesaController -> Importando de CSV a la base de datos" }
        repository.importar(csvStorage.importar())
    }

    fun buscarTodos(): List<LineaHamburguesa> {
        logger.debug { "LineaHamburguesaController -> Buscar todos" }
        return repository.buscarTodos()
    }

    fun buscarPorId(id: Long): Result<LineaHamburguesa, LineaHamburguesaException> {
        logger.debug { "LineaHamburguesaController -> Buscar por ID: $id" }
        return repository.buscarPorId(id)?.let { Ok(it) }
            ?: Err(LineaHamburguesaNoEncontradaException("No se ha encontrado la linea con ID: $id"))
    }

    fun eliminarTodos() {
        logger.debug { "LineaHamburguesaController -> Eliminar todos" }
        return repository.eliminarTodos()
    }

    fun eliminarPorId(id: Long): Result<LineaHamburguesa, LineaHamburguesaException> {
        logger.debug { "LineaHamburguesaController -> Eliminar por ID: $id" }
        return repository.eliminarPorId(id)?.let { Ok(it) }
            ?: Err(LineaHamburguesaNoEncontradaException("No se ha encontrado la linea con ID: $id"))
    }

    fun salvar(item: LineaHamburguesa): LineaHamburguesa {
        logger.debug { "LineaHamburguesaController -> Salvar linea: $item" }
        item.validar()
        return repository.salvar(item)
    }

}