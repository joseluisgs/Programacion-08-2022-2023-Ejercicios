package storage.profesor

import config.AppConfig
import factories.ModuloFactory
import factories.ModuloFactory.getModulosDefault
import factories.ProfesorFactory.getProfesoresDefault
import models.Modulo
import models.Profesor
import services.storage.modulo.ModuloFileJson
import services.storage.profesor.ProfesorFileJson
import storage.StorageGenericTest
import java.io.File

class ModuloFileJsonlTest: StorageGenericTest<Modulo>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}modulo.json"

    override fun getStorage() = ModuloFileJson

    override fun getDefault() = getModulosDefault()
}