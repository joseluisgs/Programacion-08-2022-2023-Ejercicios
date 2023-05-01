package storage.profesor

import config.AppConfig
import factories.ProfesorFactory.getProfesoresDefault
import models.Profesor
import services.storage.profesor.ProfesorFileCsv
import services.storage.profesor.ProfesorFileJson
import services.storage.profesor.ProfesorFileXML
import storage.StorageGenericTest
import java.io.File

class ProfesorFileCsvTest: StorageGenericTest<Profesor>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}profesor.csv"

    override fun getStorage() = ProfesorFileCsv

    override fun getDefault() = getProfesoresDefault()
}