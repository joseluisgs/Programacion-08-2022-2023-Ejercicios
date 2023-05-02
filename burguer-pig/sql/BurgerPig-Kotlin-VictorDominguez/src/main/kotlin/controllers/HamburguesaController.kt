package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.HamburguesaException
import exceptions.HamburguesaNoEncontradaException
import models.Hamburguesa
import mu.KotlinLogging
import repositories.hamburguesa.HamburguesaRepository
import services.storage.hamburguesa.HamburguesaService
import validators.validar
import java.util.*

private val logger = KotlinLogging.logger{}

class HamburguesaController(
    private val repository: HamburguesaRepository,
    private val csvStorage: HamburguesaService,
    private val jsonStorage: HamburguesaService
) {

    init {
        importarDeCsv()
    }

    fun exportarAJson() {
        logger.debug { "HamburguesaController -> Exportando de la base de datos a JSON" }
        jsonStorage.exportar(repository.exportar())
    }

    fun importarDeCsv() {
        logger.debug { "HamburguesaController -> Importando de CSV a la base de datos" }
        repository.importar(csvStorage.importar())
    }

    fun buscarTodos(): List<Hamburguesa> {
        logger.debug { "HamburguesaController -> Buscar todos" }
        return repository.buscarTodos()
    }

    fun buscarPorId(id: UUID): Result<Hamburguesa, HamburguesaException> {
        logger.debug { "HamburguesaController -> Buscar por ID: $id" }
        return repository.buscarPorId(id)?.let { Ok(it) }
            ?: Err(HamburguesaNoEncontradaException("No se ha encontrado la hamburguesa con ID: $id"))
    }

    fun eliminarTodos() {
        logger.debug { "HamburguesaController -> Eliminar todos" }
        return repository.eliminarTodos()
    }

    fun eliminarPorId(id: UUID): Result<Hamburguesa, HamburguesaException> {
        logger.debug { "HamburguesaController -> Eliminar por ID: $id" }
        return repository.eliminarPorId(id)?.let { Ok(it) }
            ?: Err(HamburguesaNoEncontradaException("No se ha encontrado la hamburguesa con ID: $id"))
    }

    fun salvar(item: Hamburguesa): Hamburguesa {
        logger.debug { "HamburguesaController -> Salvar hamburguesa: $item" }
        item.validar()
        return repository.salvar(item)
    }


}