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

val ControllerCsv = module {
    single { ConfigDatabase() }

    single { ConfigApp(get()) }

    single<HamburgesaStorageService>{ HamburgesasStorageServiceCsv(get()) }

    single<HamburgesaRepository> { HamburgesaRepositoryImpl(get()) }
    single<IngredienteRepository> { IngredienteRepositoryImpl(get()) }

    single<HamburgesaController>{ HamburgesaController(get(), get(), get()) }
}

@OptIn(ExperimentalStdlibApi::class)
val ControllerJson = module {
    single { ConfigDatabase() }

    single { ConfigApp(get()) }

    single<HamburgesaStorageService>{ HamburgesasStorageServiceJson(get()) }

    single<HamburgesaRepository> { HamburgesaRepositoryImpl(get()) }
    single<IngredienteRepository> { IngredienteRepositoryImpl(get()) }

    single<HamburgesaController>{ HamburgesaController(get(), get(), get()) }
}

val ControllerXml = module {
    single { ConfigDatabase() }

    single { ConfigApp(get()) }

    single<HamburgesaStorageService> { HamburgesasStorageServiceXml(get()) }

    single<HamburgesaRepository> { HamburgesaRepositoryImpl(get()) }
    single<IngredienteRepository> { IngredienteRepositoryImpl(get()) }

    single<HamburgesaController>{ HamburgesaController(get(), get(), get()) }
}

val ControllerTxt = module {
    single { ConfigDatabase() }

    single { ConfigApp(get()) }

    single<HamburgesaStorageService> { HamburgesasStorageServiceTxt(get()) }

    single<HamburgesaRepository> { HamburgesaRepositoryImpl(get()) }
    single<IngredienteRepository> { IngredienteRepositoryImpl(get()) }

    single<HamburgesaController>{ HamburgesaController(get(), get(), get()) }
}

val ControllerSer = module {
    single { ConfigDatabase() }

    single { ConfigApp(get()) }

    single<HamburgesaStorageService> { HamburgesasStorageServiceSer(get()) }

    single<HamburgesaRepository> { HamburgesaRepositoryImpl(get()) }
    single<IngredienteRepository> { IngredienteRepositoryImpl(get()) }

    single<HamburgesaController>{ HamburgesaController(get(), get(), get()) }
}