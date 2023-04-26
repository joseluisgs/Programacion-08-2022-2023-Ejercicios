package storage.profesor

import config.AppConfig
import factories.ProfesorFactory.getProfesoresDefault
import models.Profesor
import services.storage.profesor.ProfesorFileXML
import storage.StorageGenericTest
import java.io.File

class ProfesorFileXmlTest: StorageGenericTest<Profesor>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}profesor.xml"

    override fun getStorage() = ProfesorFileXML

    override fun getDefault() = getProfesoresDefault()
}