package controllers

import errors.HamburguesaErrors
import errors.IngredienteErrors
import models.Hamburguesa
import models.Ingrediente
import models.LineaHamburguesa
import mu.KotlinLogging
import repositories.hamburguesa.HamburguesaRepository
import repositories.ingredientes.IngredienteRepository
import service.storage.hamburgueas.HamburguesaStorage

private val logger = KotlinLogging.logger{}

class HamburguesaController(
    private val ingredienteRepository: IngredienteRepository,
    private val hamburguesaRepository: HamburguesaRepository,
    private val hamburguesaStorage: HamburguesaStorage
) {

    fun save(receta: List<Ingrediente>): Hamburguesa {
        logger.info { "Creando linea hamburguesa" }
        val hamburguesa = Hamburguesa(id = 0, name = "Hamburguesa")
        comprobarStock(receta)
        crearLineHamburguesa(hamburguesa, receta)
        actualizarStock(receta)
        return hamburguesaRepository.save(hamburguesa)
    }

    private fun crearLineHamburguesa(hamburguesa: Hamburguesa, receta: List<Ingrediente>) {
        logger.debug {"crearLineHamburguesa $hamburguesa $receta"}
        receta.forEach {
            val ingrediente = ingredienteRepository.findById(it.id)
                ?: throw IngredienteErrors.IngredienteNoEncontrado("Ingrediente con id ${it.id} no encontrado")
            logger.debug {"Ingrediente encontrado $ingrediente"}
            val lineaHamburguesa = LineaHamburguesa(
                lineaId = hamburguesa.nextLineaId,
                idIngrediente = ingrediente.id,
                idHamburguesa = hamburguesa.id,
                precioIngrediente = ingrediente.price,
                cantidadIngrediente = ingrediente.cantidad
            )
            hamburguesa.addLineaIngrediente(lineaHamburguesa)
        }
    }

    private fun comprobarStock(receta: List<Ingrediente>) {
        logger.debug { "Comprobando stock de ingredientes" }
        receta.forEach { item ->
            val ingrediente = ingredienteRepository.findById(item.id)
                ?: throw IngredienteErrors.IngredienteNoEncontrado("Ingrediente con id ${item.id} no encontrado")
            if (ingrediente.cantidad < item.cantidad){
                throw HamburguesaErrors.StockInsuficinete("No exite el stock suficiente para crear la hamburguesa")
            }
        }
    }

    private fun actualizarStock(receta: List<Ingrediente>) {
        logger.debug { "Actualizar stock de ingredientes" }
        receta.forEach { item ->
            val ingrediente = ingredienteRepository.findById(item.id)
               ?: throw IngredienteErrors.IngredienteNoEncontrado("Ingrediente con id ${item.id} no encontrado")
            val updated = ingrediente.copy(cantidad = ingrediente.cantidad - 1)
            ingredienteRepository.update(updated)
        }
    }

    private fun getAll(): List<Hamburguesa> {
        logger.info { "Obteniendo hamburguesas desde la base de datos" }
        return hamburguesaRepository.findAll().toList()
    }

    fun exportData(items: List<Hamburguesa>) {
        hamburguesaStorage.saveData(items)
    }

    fun loadData(): List<Hamburguesa> {
        return hamburguesaStorage.loadData()
    }
}