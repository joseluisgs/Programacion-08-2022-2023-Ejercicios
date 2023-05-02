package di.components;

import dagger.Component;
import di.modules.ProfesorStorage;
import service.storage.profesor.ProfesorStorageService;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ProfesorStorage.class
})
public interface ProfesorComponent {
    @Named("Json")ProfesorStorageService build();
}
