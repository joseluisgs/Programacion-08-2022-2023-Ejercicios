package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.IngredienteException
import exceptions.IngredienteNoEncontradoException
import models.Ingrediente
import mu.KotlinLogging
import repositories.ingrediente.IngredienteRepository
import services.storage.ingrediente.IngredienteService
import validators.validar
import java.util.*

private val logger = KotlinLogging.logger{}

class IngredienteController(
    private val repository: IngredienteRepository,
    private val csvStorage: IngredienteService,
    private val jsonStorage: IngredienteService
) {

    init {
        importarDeCsv()
    }

    fun exportarAJson() {
        logger.debug { "IngredienteController -> Exportando de la base de datos a JSON" }
        jsonStorage.exportar(repository.exportar())
    }

    fun importarDeCsv() {
        logger.debug { "IngredienteController -> Importando de CSV a la base de datos" }
        repository.importar(csvStorage.importar())
    }

    fun buscarTodos(): List<Ingrediente> {
        logger.debug { "IngredienteController -> Buscar todos" }
        return repository.buscarTodos()
    }

    fun buscarPorId(id: Long): Result<Ingrediente, IngredienteException> {
        logger.debug { "IngredienteController -> Buscar por ID: $id" }
        return repository.buscarPorId(id)?.let { Ok(it) }
            ?: Err(IngredienteNoEncontradoException("No se ha encontrado el ingrediente con ID: $id"))
    }

    fun eliminarTodos() {
        logger.debug { "IngredienteController -> Eliminar todos" }
        return repository.eliminarTodos()
    }

    fun eliminarPorId(id: Long): Result<Ingrediente, IngredienteException> {
        logger.debug { "IngredienteController -> Eliminar por ID: $id" }
        return repository.eliminarPorId(id)?.let { Ok(it) }
            ?: Err(IngredienteNoEncontradoException("No se ha encontrado el ingrediente con ID: $id"))
    }

    fun salvar(item: Ingrediente): Ingrediente {
        logger.debug { "IngredienteController -> Salvar ingrediente: $item" }
        item.validar()
        return repository.salvar(item)
    }


}