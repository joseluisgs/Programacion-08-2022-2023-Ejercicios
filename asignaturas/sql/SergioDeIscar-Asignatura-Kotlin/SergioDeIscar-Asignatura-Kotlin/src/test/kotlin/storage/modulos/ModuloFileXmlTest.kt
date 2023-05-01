package storage.profesor

import config.AppConfig
import factories.ModuloFactory
import factories.ModuloFactory.getModulosDefault
import factories.ProfesorFactory.getProfesoresDefault
import models.Modulo
import models.Profesor
import services.storage.modulo.ModuloFileXml
import services.storage.profesor.ProfesorFileXML
import storage.StorageGenericTest
import java.io.File

class ModuloFileXmlTest: StorageGenericTest<Modulo>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}modulo.xml"

    override fun getStorage() = ModuloFileXml

    override fun getDefault() = getModulosDefault()
}