package service.storage.modulo;

import config.ConfigApp;
import dto.modulo.ListaModulosDto;
import models.Modulo;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static mapper.modulo.ModuloMapper.toModulos;
import static mapper.modulo.ModuloMapper.toModulosDto;

public class ModuloStorageServiceXml implements ModuloStorageService {

    private Logger logger = LoggerFactory.getLogger(ModuloStorageServiceXml.class);

    private File file = new File(ConfigApp.getInstance().APP_DATA+File.separator+"modulos.xml");

    private Persister persister = new Persister();

    public ModuloStorageServiceXml() throws IOException {}

    @Override
    public void safeAll(List<Modulo> entities) throws Exception {
        logger.debug("Se guardan todas los módulos en el fichero XML");

        persister.write(toModulosDto(entities), file);
    }

    @Override
    public List<Modulo> loadAll() throws Exception {
        logger.debug("Se cargan todas los módulos del fichero XML");

        if(!Files.exists(file.toPath())) return Collections.emptyList();
        return toModulos(persister.read(ListaModulosDto.class, file));
    }
}
