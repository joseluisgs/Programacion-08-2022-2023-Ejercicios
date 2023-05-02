package di.components;

import dagger.Component;
import di.modules.DocenciaStorage;
import service.storage.docencia.DocenciaStorageService;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DocenciaStorage.class
})
public interface DocenciaComponent {
    @Named("Xml")DocenciaStorageService build();
}
