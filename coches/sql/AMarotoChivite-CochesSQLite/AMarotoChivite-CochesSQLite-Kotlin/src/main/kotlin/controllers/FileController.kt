package controllers

import models.dto.ConductorEmbeddedDto
import services.storages.base.IStorageGeneral

class FileController(private val conductorStorage: IStorageGeneral<ConductorEmbeddedDto>) {

    fun exportDataToFile(typeFile: String, data: List<ConductorEmbeddedDto>): Boolean {
        when (typeFile) {
            "json" -> conductorStorage.writeFileToJson(data)
            "csv" -> conductorStorage.writeFileToCsv(data)
            "xml" -> conductorStorage.writeFileToXml(data)
            else -> throw IllegalArgumentException("Tipo de fichero no apto: $typeFile")
        }
        return true
    }

    fun importDataToMain(typeFile: String): List<ConductorEmbeddedDto>? {
        return when (typeFile) {
            "json" -> {
                conductorStorage.readFileOfJson()
            }

            "csv" -> {
                conductorStorage.readFileOfCsv()
            }

            "xml" -> {
                conductorStorage.readFileOfXml()
            }

            else -> throw IllegalArgumentException("Tipo de fichero no apto: $typeFile")
        }
    }
}