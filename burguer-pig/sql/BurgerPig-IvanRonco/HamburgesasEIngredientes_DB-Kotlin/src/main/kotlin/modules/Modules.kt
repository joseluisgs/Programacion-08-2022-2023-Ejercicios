package modules

import config.ConfigApp
import controller.HamburgesaController
import org.koin.core.qualifier.named
import org.koin.dsl.module
import service.database.ConfigDatabase
import service.repository.Hamburger.HamburgesaRepository
import service.repository.Hamburger.HamburgesaRepositoryImpl
import service.repository.Ingrediente.IngredienteRepository
import service.repository.Ingrediente.IngredienteRepositoryImpl
import service.storageService.Hamburger.*
import storageService.Hamburger.HamburgesaStorageService

@OptIn(ExperimentalStdlibApi::class)
val myModule = module {
    single { ConfigDatabase() }

    single { ConfigApp(get()) }

    single<HamburgesaStorageService>(named("StorageCsv")){ HamburgesasStorageServiceCsv(get()) }
    single<HamburgesaStorageService>(named("StorageXml")){ HamburgesasStorageServiceXml(get()) }
    single<HamburgesaStorageService>(named("StorageJson")){ HamburgesasStorageServiceJson(get()) }
    single<HamburgesaStorageService>(named("StorageTxt")){ HamburgesasStorageServiceTxt(get()) }
    single<HamburgesaStorageService>(named("StorageSer")){ HamburgesasStorageServiceSer(get()) }

    single<HamburgesaRepository> { HamburgesaRepositoryImpl(get()) }
    single<IngredienteRepository> { IngredienteRepositoryImpl(get()) }

    single<HamburgesaController>{ HamburgesaController(get(), get(named("StorageCsv")), get()) }
}