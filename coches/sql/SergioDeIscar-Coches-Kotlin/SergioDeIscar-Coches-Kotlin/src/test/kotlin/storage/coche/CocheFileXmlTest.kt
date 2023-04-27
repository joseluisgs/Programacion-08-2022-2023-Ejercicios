package storage.coche

import config.AppConfig
import factories.CocheFactory.getCochesDefault
import models.Coche
import services.storage.StorageService
import services.storage.coche.CocheFileCsv
import services.storage.coche.CocheFileXml
import storage.StorageGenericTest
import java.io.File

class CocheFileXmlTest: StorageGenericTest<Coche>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}coche.xml"

    override fun getStorage() = CocheFileXml

    override fun getDefault() = getCochesDefault()
}