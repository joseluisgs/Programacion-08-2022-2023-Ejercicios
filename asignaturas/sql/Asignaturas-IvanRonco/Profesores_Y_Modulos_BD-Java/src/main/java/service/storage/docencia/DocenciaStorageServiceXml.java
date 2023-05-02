package service.storage.docencia;

import config.ConfigApp;
import dto.docencia.ListaDocenciasDto;
import models.Docencia;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static mapper.docencia.DocenciaMapper.toDocencias;
import static mapper.docencia.DocenciaMapper.toDocenciasDto;

public class DocenciaStorageServiceXml implements DocenciaStorageService{
    private Logger logger = LoggerFactory.getLogger(DocenciaStorageServiceXml.class);

    private File file = new File(ConfigApp.getInstance().APP_DATA+File.separator+"docencias.xml");

    private Persister persister = new Persister();

    @Inject
    public DocenciaStorageServiceXml() {
    }

    @Override
    public void safeAll(List<Docencia> entities) throws Exception {
        logger.debug("Se guardan todas las docencias en el fichero XML");

        persister.write(toDocenciasDto(entities), file);
    }

    @Override
    public List<Docencia> loadAll() throws Exception {
        logger.debug("Se cargan todas las docencias del fichero XML");

        if(!Files.exists(file.toPath())) return Collections.emptyList();
        return toDocencias(persister.read(ListaDocenciasDto.class, file ));
    }
}
