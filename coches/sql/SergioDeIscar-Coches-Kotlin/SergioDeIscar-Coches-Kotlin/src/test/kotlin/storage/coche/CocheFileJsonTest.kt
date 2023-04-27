package storage.coche

import config.AppConfig
import factories.CocheFactory.getCochesDefault
import models.Coche
import services.storage.StorageService
import services.storage.coche.CocheFileCsv
import services.storage.coche.CocheFileJson
import storage.StorageGenericTest
import java.io.File

class CocheFileJsonTest: StorageGenericTest<Coche>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}coche.json"

    override fun getStorage() = CocheFileJson

    override fun getDefault() = getCochesDefault()
}