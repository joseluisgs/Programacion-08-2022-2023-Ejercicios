package service.storage.docencia;

import config.ConfigApp;
import dto.docencia.DocenciaDto;
import models.Docencia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mapper.docencia.DocenciaMapper.*;

public class DocenciaStorageServiceCsv implements DocenciaStorageService{

    private Logger logger = LoggerFactory.getLogger(DocenciaStorageServiceCsv.class);

    private File file = new File(ConfigApp.getInstance().APP_DATA+File.separator+"docencias.csv");

    public DocenciaStorageServiceCsv() throws IOException {}

    @Override
    public void safeAll(List<Docencia> entities) {
        logger.debug("Se guardan todas las docencias en el fichero CSV");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("profesor_id;modulo_id;grupo"+"\n");

            for(int i = 0;i<entities.stream().map(it -> toDocenciaDto(it)).toList().size(); i++){
                bw.append(entities.get(i).getProfesor()+";"+entities.get(i).getModulo()+";"+entities.get(i).getGrupo()+"\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Docencia> loadAll() throws IOException {
        logger.debug("Se cargan todas las docencias del fichero CSV");

        if(!Files.exists(file.toPath())) return Collections.emptyList();
        List<Docencia> docencias = new ArrayList<>();
        return Files.readAllLines(file.toPath()).stream().skip(1)
                .map(it -> it.split(";"))
                .map(it -> Arrays.stream(it).map(they -> they.trim()))
                .map(it -> it.toList())
                .map(it ->
                        toDocencia(new DocenciaDto(
                                it.get(0),
                                it.get(1),
                                it.get(2)
                        )))
                .toList();
    }
}
