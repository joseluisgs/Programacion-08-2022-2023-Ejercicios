package storage.profesor

import config.AppConfig
import factories.ProfesorFactory.getProfesoresDefault
import models.Profesor
import services.storage.profesor.ProfesorFileJson
import services.storage.profesor.ProfesorFileXML
import storage.StorageGenericTest
import java.io.File

class ProfesorFileJsonlTest: StorageGenericTest<Profesor>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}profesor.json"

    override fun getStorage() = ProfesorFileJson

    override fun getDefault() = getProfesoresDefault()
}