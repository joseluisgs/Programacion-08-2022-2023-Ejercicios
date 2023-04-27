package storage.coche

import config.AppConfig
import factories.CocheFactory.getCochesDefault
import models.Coche
import services.storage.StorageService
import services.storage.coche.CocheFileCsv
import storage.StorageGenericTest
import java.io.File

class CocheFileCsvTest: StorageGenericTest<Coche>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}coche.csv"

    override fun getStorage() = CocheFileCsv

    override fun getDefault() = getCochesDefault()
}