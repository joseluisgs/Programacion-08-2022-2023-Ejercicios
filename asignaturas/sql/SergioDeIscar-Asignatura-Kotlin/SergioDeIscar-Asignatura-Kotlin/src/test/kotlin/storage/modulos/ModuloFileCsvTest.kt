package storage.profesor

import config.AppConfig
import factories.ModuloFactory.getModulosDefault
import factories.ProfesorFactory.getProfesoresDefault
import models.Modulo
import models.Profesor
import services.storage.modulo.ModuloFileCsv
import services.storage.profesor.ProfesorFileCsv
import storage.StorageGenericTest
import java.io.File

class ModuloFileCsvTest: StorageGenericTest<Modulo>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}modulo.csv"

    override fun getStorage() = ModuloFileCsv

    override fun getDefault() = getModulosDefault()
}