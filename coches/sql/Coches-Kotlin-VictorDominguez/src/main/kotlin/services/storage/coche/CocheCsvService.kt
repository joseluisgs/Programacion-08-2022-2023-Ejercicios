package services.storage.coche

import enums.TipoMotor
import models.Coche
import mu.KotlinLogging
import java.io.File
import java.util.*

private val logger = KotlinLogging.logger{}

object CocheCsvService: CochesService {

    private val path = "${System.getProperty("user.dir")}${File.separator}src${File.separator}main${File.separator}resources${File.separator}coches.csv"
    private val fichero = File(path)

    override fun exportar(items: List<Coche>) {
        logger.debug { "CocheCsvService -> Exportando datos a CSV" }
    }

    override fun importar(): List<Coche> {
        logger.debug { "CocheCsvService -> Importando datos de CSV" }
        return fichero.readLines()
            .drop(1)
            .map { linea -> linea.split(";") }
            .map { columnas ->
                Coche(
                    columnas[0].toLong(),
                    UUID.fromString(columnas[1]),
                    columnas[2],
                    columnas[3],
                    columnas[4].toDouble(),
                    TipoMotor.valueOf(columnas[5])
                )
            }
    }
}