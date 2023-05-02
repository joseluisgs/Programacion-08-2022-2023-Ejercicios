package services.storage.ingrediente

import models.Ingrediente
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger{}

object IngredienteCsvService: IngredienteService {

    private val path = "${System.getProperty("user.dir")}${File.separator}src${File.separator}main${File.separator}resources${File.separator}ingredientes.csv"
    private val fichero = File(path)

    override fun exportar(items: List<Ingrediente>) {
        TODO("Not yet implemented")
    }

    override fun importar(): List<Ingrediente> {
        logger.debug { "IngredienteCsvService -> Importando datos de CSV" }
        return fichero.readLines()
            .drop(1)
            .map { linea -> linea.split(";") }
            .map { columnas ->
                Ingrediente(
                    columnas[0].toLong(),
                    columnas[1],
                    columnas[2].toDouble()
                )
            }
    }
}