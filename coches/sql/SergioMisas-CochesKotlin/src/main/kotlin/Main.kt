import controllers.CocheController
import repositories.CocheRepositoryImpl
import services.DatabaseManager

fun main() {
    DatabaseManager
    val controlador = CocheController()
    val csv = controlador.leerCsv()
    csv.forEach { controlador.update(it) }
    controlador.findAll()
}
