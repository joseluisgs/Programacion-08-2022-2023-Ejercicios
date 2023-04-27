import controllers.CocheController
import controllers.ConductorController
import repositories.Coches.CocheRepository
import repositories.conductores.ConductorRepository
import services.base.Storage
import services.coches.CocheStorageImp
import services.coches.CocheStorageImp.loadDataFromCsv
import services.conductor.ConductorStorageImp
import services.database.CochesDataBaseService
import java.lang.ModuleLayer.Controller


fun main(){
    CochesDataBaseService
    val controllerCoche = CocheController(CocheRepository, CocheStorageImp)
    val controllerConductor = ConductorController(ConductorRepository, ConductorStorageImp)


    controllerConductor.saveAll(ConductorStorageImp.loadDatafromJson())


}