package storage.conductor

import config.AppConfig
import factories.CocheFactory.getCochesDefault
import factories.ConductorFactory.getConductorDefault
import models.Conductor
import services.storage.coche.CocheFileXml
import services.storage.conductor.ConductorFileCsv
import services.storage.conductor.ConductorFileXml
import storage.StorageGenericTest
import java.io.File

class ConductorFileCsvTest: StorageGenericTest<Conductor>() {
    override fun filePath() = "${AppConfig.APP_DATA}${File.separator}conductor.csv"

    override fun getStorage() = ConductorFileCsv

    override fun getDefault() = getConductorDefault()
}