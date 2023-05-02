package service.storage.modulo;

import config.ConfigApp;
import dto.docencia.DocenciaDto;
import dto.modulo.ModuloDto;
import models.Docencia;
import models.Modulo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.storage.docencia.DocenciaStorageServiceCsv;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mapper.docencia.DocenciaMapper.toDocencia;
import static mapper.modulo.ModuloMapper.toModulo;
import static mapper.modulo.ModuloMapper.toModuloDto;

public class ModuloStorageServiceCsv implements ModuloStorageService {
    private Logger logger = LoggerFactory.getLogger(DocenciaStorageServiceCsv.class);

    private File file = new File(ConfigApp.getInstance().APP_DATA+File.separator+"modulos.csv");

    @Inject
    public ModuloStorageServiceCsv() {
    }

    @Override
    public void safeAll(List<Modulo> entities) {
        logger.debug("Se guardan todas las docencias en el fichero CSV");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("uuid;nombre;curso;grado"+"\n");

            for(int i = 0;i<entities.stream().map(it -> toModuloDto(it)).toList().size(); i++){
                bw.append(entities.get(i).getUuid()+";"+entities.get(i).getNombre()+";"+entities.get(i).getCurso()+";"+entities.get(i).getGrado()+"\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Modulo> loadAll() throws IOException {
        logger.debug("Se cargan todas las docencias del fichero CSV");

        if(!Files.exists(file.toPath())) return Collections.emptyList();
        List<Docencia> docencias = new ArrayList<>();
        return Files.readAllLines(file.toPath()).stream().skip(1)
                .map(it -> it.split(";"))
                .map(it -> Arrays.stream(it).map(they -> they.trim()))
                .map(it -> it.toList())
                .map(it ->
                        toModulo(new ModuloDto(
                                it.get(0),
                                it.get(1),
                                it.get(2),
                                it.get(3)
                        )))
                .toList();
    }
}
