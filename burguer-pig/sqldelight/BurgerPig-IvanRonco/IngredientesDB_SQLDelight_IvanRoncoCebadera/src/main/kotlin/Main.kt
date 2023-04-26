import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import controller.IngredientesController
import service.database.ingredientes.IngredientesDatabase
import factory.IngredientesFactory
import repositorio.ingredientes.IngredienteRepositorySQLite
import service.storage.ingredientes.IngredienteStorageServiceCsv

fun main(args: Array<String>) {

    val ingredientes = IngredientesFactory.createSome()

    val controller = IngredientesController(
        IngredienteRepositorySQLite,
        IngredienteStorageServiceCsv()
    )

    ingredientes.forEach {
        controller.save(it)
    }

    controller.findAll().forEach { println(it) }

    controller.exportData()

    controller.findAll().forEach { println(it) }

    controller.findByDisponible(true).forEach { println(it) }

    controller.findById(12).onSuccess { println(it) }.onFailure { System.err.println("Error ${it.message}") }

    controller.importData(true)

    controller.findAll().forEach { println(it) }
}