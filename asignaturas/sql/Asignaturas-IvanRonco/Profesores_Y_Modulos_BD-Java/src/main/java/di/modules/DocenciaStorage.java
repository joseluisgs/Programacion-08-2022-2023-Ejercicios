package di.modules;

import dagger.Binds;
import dagger.Module;
import service.storage.docencia.DocenciaStorageService;
import service.storage.docencia.DocenciaStorageServiceCsv;
import service.storage.docencia.DocenciaStorageServiceJson;
import service.storage.docencia.DocenciaStorageServiceXml;
import service.storage.profesor.ProfesorStorageService;
import service.storage.profesor.ProfesorStorageServiceCsv;
import service.storage.profesor.ProfesorStorageServiceJson;
import service.storage.profesor.ProfesorStorageServiceXml;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public interface DocenciaStorage {
    @Singleton
    @Binds
    @Named("Xml")
    DocenciaStorageService bindProfStoraXml(DocenciaStorageServiceXml impl);

    @Singleton
    @Binds
    @Named("Json")
    DocenciaStorageService bindProfStoraJson(DocenciaStorageServiceJson impl);

    @Singleton
    @Binds
    @Named("Csv")
    DocenciaStorageService bindProfStoraCsv(DocenciaStorageServiceCsv impl);
}
