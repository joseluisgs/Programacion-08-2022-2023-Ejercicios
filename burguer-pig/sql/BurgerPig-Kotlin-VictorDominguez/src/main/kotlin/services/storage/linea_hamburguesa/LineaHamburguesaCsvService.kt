package services.storage.linea_hamburguesa

import models.LineaHamburguesa
import mu.KotlinLogging
import java.io.File
import java.util.*

private val logger = KotlinLogging.logger{}

object LineaHamburguesaCsvService: LineaHamburguesaService {

    private val path = "${System.getProperty("user.dir")}${File.separator}src${File.separator}main${File.separator}resources${File.separator}linea_hamburguesa.csv"
    private val fichero = File(path)

    override fun exportar(items: List<LineaHamburguesa>) {
        TODO("Not yet implemented")
    }

    override fun importar(): List<LineaHamburguesa> {
        return fichero.readLines()
            .drop(1)
            .map { linea -> linea.split(";") }
            .map { columnas ->
                LineaHamburguesa(
                    columnas[0].toLong(),
                    UUID.fromString(columnas[1]),
                    columnas[2].toLong(),
                    columnas[3].toInt()
                )
            }
    }
}