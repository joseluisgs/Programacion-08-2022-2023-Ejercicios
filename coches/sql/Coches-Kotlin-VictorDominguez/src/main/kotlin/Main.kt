import controllers.CochesController
import controllers.ConductoresController
import repositories.coche.CochesRepositoryMemory
import repositories.conductor.ConductoresRepositoryMemory
import services.storage.ConductorCocheJsonService
import services.storage.coche.CocheCsvService
import services.storage.coche.CocheJsonService
import services.storage.conductor.ConductorCsvService
import services.storage.conductor.ConductorJsonService
import java.io.File

fun main(args: Array<String>) {

    val cocheController = CochesController(
        CochesRepositoryMemory(),
        CocheCsvService,
        CocheJsonService
    )
    val conductorController = ConductoresController(
        ConductoresRepositoryMemory(),
        ConductorCsvService,
        ConductorJsonService
    )

    val listaCoches = cocheController.buscarTodos()
    val listaConductores = conductorController.buscarTodos()

    cocheController.exportarAJson()
    conductorController.exportarAJson()

    for (coche in listaCoches) {
        listaConductores.forEach {
            if (it.id == coche.idCond) {
                it.coches.add(coche)
            }
        }
    }

    val jsonFinalStorage = ConductorCocheJsonService

    jsonFinalStorage.exportar(listaConductores)

}