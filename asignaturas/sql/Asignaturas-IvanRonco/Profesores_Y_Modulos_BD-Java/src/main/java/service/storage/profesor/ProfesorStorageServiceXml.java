package service.storage.profesor;

import config.ConfigApp;
import dto.profesor.ListaProfesoresDto;
import models.Profesor;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static mapper.profesor.ProfesorMapper.toProfesores;
import static mapper.profesor.ProfesorMapper.toProfesoresDto;

public class ProfesorStorageServiceXml implements ProfesorStorageService {
    private Logger logger = LoggerFactory.getLogger(ProfesorStorageServiceXml.class);

    private File file = new File(ConfigApp.getInstance().APP_DATA+File.separator+"profesores.xml");

    private Persister persister = new Persister();

    @Inject
    public ProfesorStorageServiceXml() {
    }

    @Override
    public void safeAll(List<Profesor> entities) throws Exception {
        logger.debug("Se guardan todas los profesores en el fichero XML");

        persister.write(toProfesoresDto(entities), file);
    }

    @Override
    public List<Profesor> loadAll() throws Exception {
        logger.debug("Se cargan todas los profesores del fichero XML");

        if(!Files.exists(file.toPath())) return Collections.emptyList();
        return toProfesores(persister.read(ListaProfesoresDto.class, file));
    }
}
