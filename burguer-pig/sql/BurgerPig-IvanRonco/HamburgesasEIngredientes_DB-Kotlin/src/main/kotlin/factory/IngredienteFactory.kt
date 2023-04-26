package factory

import model.Ingrediente
import mu.KotlinLogging

object IngredienteFactory {

    private val logger = KotlinLogging.logger {}

    fun createSomeRandom(): List<Ingrediente> {
        logger.debug { "Se inicia el factory que creará algún ingrediente aleatorio" }
        val ingredientes = mutableListOf<Ingrediente>()
        val nombres = arrayOf("Salsa", "Carne", "Lechuga", "Cebolla", "Pepinillo", "Pan")
        val numero = (1..6).random()
        for(i in 1..numero){
            val precio: Double = ((0..750).random() / 100.0)
            var ingrediente: Ingrediente = Ingrediente(0L , nombres.random(), precio)
            ingredientes.add(ingrediente)
        }
        return ingredientes.toList()
    }

}
