package factory

import models.Ingrediente
import java.time.LocalDate
import java.util.UUID

object IngredientesFactory {
    fun createSome(): List<Ingrediente>{
        val ingredientes = mutableListOf<Ingrediente>()
        val nombres = listOf<String>("Cebolla", "Carne", "Oliva", "Aceite", "Almendras")
        repeat((1..5).random()) {
            ingredientes.add(
                Ingrediente(
                    uuid = UUID.randomUUID(),
                    nombre = nombres.random(),
                    precio = (((0..100).random())/100.0)*100.0,
                    cantidad = (0..15).random(),
                    createAt = LocalDate.now(),
                    updatedAt = LocalDate.now(),
                    disponible = (1..2).random() == 1
                )
            )
        }
        return ingredientes
    }
}