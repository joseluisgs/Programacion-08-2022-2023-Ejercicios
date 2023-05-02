package di.modules;

import dagger.Binds;
import dagger.Module;
import service.storage.profesor.ProfesorStorageService;
import service.storage.profesor.ProfesorStorageServiceCsv;
import service.storage.profesor.ProfesorStorageServiceJson;
import service.storage.profesor.ProfesorStorageServiceXml;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public interface ProfesorStorage {
    @Singleton
    @Binds
    @Named("Xml")
    ProfesorStorageService bindProfStoraXml(ProfesorStorageServiceXml impl);

    @Singleton
    @Binds
    @Named("Json")
    ProfesorStorageService bindProfStoraJson(ProfesorStorageServiceJson impl);

    @Singleton
    @Binds
    @Named("Csv")
    ProfesorStorageService bindProfStoraCsv(ProfesorStorageServiceCsv impl);
}
