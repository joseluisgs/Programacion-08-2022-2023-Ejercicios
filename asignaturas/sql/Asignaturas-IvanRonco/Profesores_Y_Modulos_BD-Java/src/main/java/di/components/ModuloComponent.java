package di.components;

import dagger.Component;
import di.modules.ModuloStorage;
import service.storage.modulo.ModuloStorageService;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ModuloStorage.class
})
public interface ModuloComponent {
    @Named("Xml")
    ModuloStorageService build();
}
