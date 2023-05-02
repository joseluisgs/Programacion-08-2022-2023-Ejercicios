package service.storage.docencia;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import config.ConfigApp;
import dto.docencia.ListaDocenciasDto;
import models.Docencia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static mapper.docencia.DocenciaMapper.toDocencias;
import static mapper.docencia.DocenciaMapper.toDocenciasDto;

public class DocenciaStorageServiceJson implements DocenciaStorageService{
    private Logger logger = LoggerFactory.getLogger(DocenciaStorageServiceJson.class);

    private File file = new File(ConfigApp.getInstance().APP_DATA+File.separator+"docencias.json");

    private Moshi moshi= new Moshi.Builder().build();
    private JsonAdapter adapter = moshi.adapter(ListaDocenciasDto.class);

    @Inject
    public DocenciaStorageServiceJson() {
    }

    @Override
    public void safeAll(List<Docencia> entities) {
        logger.debug("Se guardan todas las docencias en el fichero JSON");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(adapter.indent("   ").toJson(toDocenciasDto(entities)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Docencia> loadAll() {
        logger.debug("Se cargan todas las docencias del fichero JSON");
        if(!Files.exists(file.toPath())) return Collections.emptyList();
        List<Docencia> docencias = new ArrayList<>();
        try{
            docencias = toDocencias((ListaDocenciasDto) adapter.fromJson(Files.readString(Path.of(file.getPath()))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return docencias;
    }
}
