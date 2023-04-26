import config.ConfigApp
import controllers.BurguerController
import models.Burguer
import models.Ingrediente
import models.LineaBurguer
import repositories.BurguerRepository
import repositories.IngredienteRepository
import services.database.DataBaseManagerGeneralDDL
import services.storages.BurguerStorageCsv
import services.storages.BurguerStorageJson
import java.time.LocalDateTime
import java.util.*

fun main(){
    // Cargamos config
    ConfigApp
    // Creamos tablas
    DataBaseManagerGeneralDDL

    // Inicializo el controlador de hamburguesas
    val controllerBurguer = BurguerController(BurguerRepository(),BurguerStorageCsv(),BurguerStorageJson())

    // Almaceno el ingrediente que voy a utilizar el mi almacén de ingreidentes (se debería utilizar controlador, pero para ahorrarme tiempo introduzco directamente)
    val repoIngredient = IngredienteRepository()
    val ingredienteSaved = repoIngredient.saveItem(Ingrediente(0,"Tomate",1.02,1, LocalDateTime.now(),LocalDateTime.now(),true))

    // Lineas de Burguer
    val listLinea = mutableListOf<LineaBurguer>()
    val newUUIDburguer = UUID.randomUUID()
    listLinea.add(LineaBurguer(0, newUUIDburguer,ingredienteSaved.id, 2,ingredienteSaved.price))

    // Guardamos burguer en CSV
    controllerBurguer.setFileType(BurguerController.FileType.CSV)

    val newBurguer = Burguer(newUUIDburguer,"prueba",1,listLinea)
    controllerBurguer.saveItem(newBurguer)

    // Escribimos en CSV para iniciar el enunciado!
    controllerBurguer.exportFile()
    // Borro la bse de datos para no interferir en el enunciado
    controllerBurguer.deleteAllItem()

    // Aquí inicia el enunciado, leemos del CSV y exportamos JSON los datos leídos
    controllerBurguer.importFile().forEach{
        controllerBurguer.saveItem(it)
    }

    controllerBurguer.setFileType(BurguerController.FileType.JSON)
    controllerBurguer.exportFile()
}