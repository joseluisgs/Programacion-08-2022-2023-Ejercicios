import controller.VehiculosController
import factory.vehiculo.VehiculosFactory
import repositorio.vehiculos.VehiculosRepositorySQLite
import service.database.vehiculos.ConfigDatabase

fun main(args: Array<String>) {

    val ingredientesDatabase = ConfigDatabase

    val vehiculos = VehiculosFactory.createSome()

    val controller = VehiculosController(
        VehiculosRepositorySQLite
    )

    controller.findAll().forEach {
        println(it)
    }

    vehiculos.forEach {
        controller.save(it)
    }

    controller.findAll().forEach { println(it) }
}