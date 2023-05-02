package services.storage.hamburguesa

import models.Hamburguesa
import mu.KotlinLogging
import java.io.File
import java.util.*

private val logger = KotlinLogging.logger{}

object HamburguesaCsvService: HamburguesaService {

    private val path = "${System.getProperty("user.dir")}${File.separator}src${File.separator}main${File.separator}resources${File.separator}hamburguesas.csv"
    private val fichero = File(path)

    override fun exportar(items: List<Hamburguesa>) {
        logger.debug { "HamburguesaCsvService -> Exportando datos a CSV" }

    }

    override fun importar(): List<Hamburguesa> {
        logger.debug { "HamburguesaCsvService -> Importando datos de CSV" }
        return fichero.readLines()
            .drop(1)
            .map { linea -> linea.split(";") }
            .map { columnas ->
                Hamburguesa(
                    UUID.fromString(columnas[0]),
                    columnas[1],
                    mutableListOf()
                )
            }
    }
}