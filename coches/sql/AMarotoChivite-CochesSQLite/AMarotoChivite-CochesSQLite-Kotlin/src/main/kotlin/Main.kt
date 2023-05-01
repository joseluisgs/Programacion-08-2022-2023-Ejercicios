import config.ConfigApp
import controllers.ConductorController
import controllers.FileController
import controllers.VehicleController
import models.Conductor
import models.Vehicle
import models.dto.ConductorEmbeddedDto
import repositories.ConductorRepository
import repositories.VehicleRepository
import services.database.DataBaseManagerGeneralDDL
import services.storages.ConductorEmbeddedVehicleStorage
import services.storages.ConductorStorage
import services.storages.VehicleStorage
import utils.mappers.VehicleListMapper
import java.util.*

fun main() {
    // Cargamos nuestra configuración
    ConfigApp

    // Cargamos la base de datos
    DataBaseManagerGeneralDDL

    // Instanciamos los controladores de cada objeto
    val conductorControlador = ConductorController(
        ConductorRepository(),
        ConductorStorage()
    )

    val vehicleControlador = VehicleController(
        VehicleRepository(),
        VehicleStorage()
    )

    // Guardo tres objetos para no tener la base de datos vacía
    repeat(3) {
        val uuid = UUID.randomUUID()
        conductorControlador.saveItem(Conductor(uuid, "aaaa"))
        vehicleControlador.saveItem(Vehicle(UUID.randomUUID(), "aaaa", Vehicle.TypeMotor.HIBRIDO, uuid))
    }

    // Recogemos todos los items
    val listConductores = conductorControlador.getAll()
    val listVehicle = vehicleControlador.getAll()

    // Agrupo la lista de vehículos por aquellos que tengan el mismo CONDUCTOR
    val mapVehicleByConductor = listVehicle.groupBy {
        it.foreignUuidConductor
    }

    // Mediante nuestro DTO, embebemos los datos y procedemos a utilizarlo en ficheros
    val listConductorWithVehicles = mutableListOf<ConductorEmbeddedDto>()

    listConductores.forEach { conductor ->
        // Buscamos los vehículos por UUID del conductor
        val uuidConductor = conductor.uuid
        val vehicles = mapVehicleByConductor[uuidConductor]!!.map { vehicle ->
            Vehicle(vehicle.uuid, vehicle.model, vehicle.motor, vehicle.foreignUuidConductor)
        }

        // Transformamos a DTO los vehiculos
        val vehiclesDto = VehicleListMapper().toDtoList(vehicles).dtoList

        // Añadimos el conductor con sus vehículos embebidos
        val newConductorWithVehicles =
            ConductorEmbeddedDto(uuidConductor.toString(), conductor.name, vehiclesDto)

        listConductorWithVehicles.add(newConductorWithVehicles)
    }

    println(listConductorWithVehicles.joinToString("\n"))

    // Escribimos el CSV
    val fileController = FileController(ConductorEmbeddedVehicleStorage())
    fileController.exportDataToFile("csv", listConductorWithVehicles)

    // Leemos el CSV y escribimos en JSON
    fileController.exportDataToFile("json", fileController.importDataToMain("csv")!!)

}