package di.modules;

import dagger.Binds;
import dagger.Module;
import service.storage.modulo.ModuloStorageService;
import service.storage.modulo.ModuloStorageServiceCsv;
import service.storage.modulo.ModuloStorageServiceJson;
import service.storage.modulo.ModuloStorageServiceXml;
import service.storage.profesor.ProfesorStorageService;
import service.storage.profesor.ProfesorStorageServiceCsv;
import service.storage.profesor.ProfesorStorageServiceJson;
import service.storage.profesor.ProfesorStorageServiceXml;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public interface ModuloStorage {
    @Singleton
    @Binds
    @Named("Xml")
    ModuloStorageService bindProfStoraXml(ModuloStorageServiceXml impl);

    @Singleton
    @Binds
    @Named("Json")
    ModuloStorageService bindProfStoraJson(ModuloStorageServiceJson impl);

    @Singleton
    @Binds
    @Named("Csv")
    ModuloStorageService bindProfStoraCsv(ModuloStorageServiceCsv impl);
}
