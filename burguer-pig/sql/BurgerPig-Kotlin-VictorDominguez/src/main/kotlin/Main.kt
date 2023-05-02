import controllers.HamburguesaController
import controllers.IngredienteController
import controllers.LineaHamburguesaController
import repositories.hamburguesa.HamburguesaRepositoryMemory
import repositories.ingrediente.IngredienteRepositoryMemory
import repositories.linea_hamburguesa.LineaHamburguesaRepositoryMemory
import services.storage.HamburguesasIngredientesJsonService
import services.storage.database.DataBaseManager
import services.storage.hamburguesa.HamburguesaCsvService
import services.storage.hamburguesa.HamburguesaJsonService
import services.storage.ingrediente.IngredienteCsvService
import services.storage.ingrediente.IngredienteJsonService
import services.storage.linea_hamburguesa.LineaHamburguesaCsvService
import java.util.*

fun main(args: Array<String>) {

    val hamburguesaController = HamburguesaController(
        HamburguesaRepositoryMemory(),
        HamburguesaCsvService,
        HamburguesaJsonService
    )

    val ingredienteController = IngredienteController(
        IngredienteRepositoryMemory(),
        IngredienteCsvService,
        IngredienteJsonService
    )

    val lineaController = LineaHamburguesaController(
        LineaHamburguesaRepositoryMemory(),
        LineaHamburguesaCsvService
    )

    val database = DataBaseManager

    val hamburguesas = hamburguesaController.buscarTodos()
    val ingredientes = ingredienteController.buscarTodos()

    val sql = "SELECT THamburguesas.id, TIngredientes.id, TLinea_Hamburguesa.cantidad " + //seleccionamos los datos que necesitemos
            "FROM THamburguesas " +
            "JOIN TLinea_Hamburguesa ON THamburguesas.id = TLinea_Hamburguesa.id_ham " +
            "JOIN TIngredientes ON TLinea_Hamburguesa.id_ing = TIngredientes.id" //juntamos las tablas que necesitamos para obtener los datos

    database.connection.prepareStatement(sql).use { stm ->
        val result = stm.executeQuery()

        while (result.next()) {
            hamburguesas.first { it.id == UUID.fromString(result.getString(1)) } //buscamos la hamburguesa en nuestra lista que coincida con la linea de la consulta
                .ingredientes
                .let {
                    var cantidad = result.getInt(3)
                    while (cantidad > 0) {
                        it.add(ingredientes.first{ ingrediente ->  ingrediente.id == result.getLong(2) }) //añadimos los ingredientes buscandolo en nuestra lista
                        cantidad--
                    }
                }
        }
    }

    hamburguesas.forEach { hamburguesa -> hamburguesa.precio = hamburguesa.ingredientes.sumOf { it.precio } } //recalculamos el precio

    val jsonFinal = HamburguesasIngredientesJsonService

    jsonFinal.exportar(hamburguesas) //exportamos

}