import controller.ConductorController
import controller.VehiculosController
import factory.conductor.ConductoresFactory
import repositorio.conductor.ConductorRepositoryImpl
import repositorio.vehiculos.VehiculosRepositoryImpl
import service.database.vehiculos.ConfigDatabase

fun main(args: Array<String>) {

    val ingredientesDatabase = ConfigDatabase

    ingredientesDatabase.initDatabase("dropAndCreate.sql")

    val controllerVehiculo = VehiculosController(
        VehiculosRepositoryImpl
    )

    val controllerConductor = ConductorController(
        ConductorRepositoryImpl
    )

    val conductores = ConductoresFactory.createSomoConductores()

    controllerConductor.findAll().forEach { println(it) }

    conductores.forEach {
        controllerConductor.save(it)
    }

    controllerConductor.findAll().forEach { println(it) }

    controllerConductor.findAll().forEach { println(it.vehiculos.size) }

    controllerConductor.deleteAll(true)

    controllerConductor.findAll().forEach { println(it) }
}